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
import com.example.harrypotter.dto.StudentDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentsActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter

    private val mockStudents = mapOf(
        "gryffindor" to listOf(
            StudentDTO("Harry Potter", "Gryffindor"),
            StudentDTO("Hermione Granger", "Gryffindor"),
            StudentDTO("Ron Weasley", "Gryffindor"),
            StudentDTO("Neville Longbottom", "Gryffindor"),
            StudentDTO("Ginny Weasley", "Gryffindor"),
            StudentDTO("Seamus Finnigan", "Gryffindor"),
            StudentDTO("Dean Thomas", "Gryffindor"),
            StudentDTO("Parvati Patil", "Gryffindor"),
        ),
        "slytherin" to listOf(
            StudentDTO("Draco Malfoy", "Slytherin"),
            StudentDTO("Pansy Parkinson", "Slytherin"),
            StudentDTO("Vincent Crabbe", "Slytherin"),
            StudentDTO("Gregory Goyle", "Slytherin"),
            StudentDTO("Blaise Zabini", "Slytherin"),
            StudentDTO("Millicent Bulstrode", "Slytherin"),
        ),
        "hufflepuff" to listOf(
            StudentDTO("Cedric Diggory", "Hufflepuff"),
            StudentDTO("Nymphadora Tonks", "Hufflepuff"),
            StudentDTO("Hannah Abbott", "Hufflepuff"),
            StudentDTO("Ernie Macmillan", "Hufflepuff"),
            StudentDTO("Justin Finch-Fletchley", "Hufflepuff"),
            StudentDTO("Susan Bones", "Hufflepuff"),
        ),
        "ravenclaw" to listOf(
            StudentDTO("Luna Lovegood", "Ravenclaw"),
            StudentDTO("Cho Chang", "Ravenclaw"),
            StudentDTO("Padma Patil", "Ravenclaw"),
            StudentDTO("Terry Boot", "Ravenclaw"),
            StudentDTO("Michael Corner", "Ravenclaw"),
            StudentDTO("Anthony Goldstein", "Ravenclaw"),
        ),
    )

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

            val students = withContext(Dispatchers.IO) {
                // TODO: HttpClient.getStudentsByHouse(casa)
                mockStudents[casa] ?: emptyList()
            }

            progressBar.visibility = View.GONE
            adapter.updateData(students)
        }
    }
}
