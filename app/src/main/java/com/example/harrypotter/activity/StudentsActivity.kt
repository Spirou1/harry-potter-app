package com.example.harrypotter.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotter.R
import com.example.harrypotter.adapter.StudentAdapter
import com.example.harrypotter.api.RetrofitClient
import com.example.harrypotter.dto.StudentDTO
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentsActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_students)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        radioGroup   = findViewById(R.id.rg_houses)
        progressBar  = findViewById(R.id.progress_bar)
        recyclerView = findViewById(R.id.rv_students)

        adapter = StudentAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.btn_voltar_students).setOnClickListener {
            finish()
        }
    }

    fun buscarEstudantes(view: View) {
        val casa = when (radioGroup.checkedRadioButtonId) {
            R.id.rb_gryffindor -> "gryffindor"
            R.id.rb_slytherin  -> "slytherin"
            R.id.rb_hufflepuff -> "hufflepuff"
            R.id.rb_ravenclaw  -> "ravenclaw"
            else               -> return
        }

        CoroutineScope(Dispatchers.Main).launch {
            progressBar.visibility = View.VISIBLE

            try {
                val students = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.getStudentsByHouse(casa)
                }

                if (students.isNotEmpty()) {
                    adapter.updateData(students)
                } else {
                    Toast.makeText(this@StudentsActivity, "Nenhum estudante encontrado", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@StudentsActivity, "Erro ao carregar estudantes", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
