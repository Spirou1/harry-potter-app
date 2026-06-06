package com.example.harrypotter.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.harrypotter.R
import com.example.harrypotter.api.RetrofitClient
import com.example.harrypotter.dto.StaffDTO
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StaffActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var layoutResult: LinearLayout
    private lateinit var tvName: TextView
    private lateinit var tvAlternate: TextView
    private lateinit var tvSpecies: TextView
    private lateinit var tvHouse: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_staff)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        etName       = findViewById(R.id.et_staff_name)
        progressBar  = findViewById(R.id.progress_bar)
        layoutResult = findViewById(R.id.layout_result)
        tvName       = findViewById(R.id.tv_staff_name)
        tvAlternate  = findViewById(R.id.tv_staff_alternate)
        tvSpecies    = findViewById(R.id.tv_staff_species)
        tvHouse      = findViewById(R.id.tv_staff_house)

        findViewById<Button>(R.id.button_voltar2).setOnClickListener {
            finish()
        }
    }

    fun buscarProfessor(view: View) {
        val nomeDigitado = etName.text.toString().trim()
        if (nomeDigitado.isEmpty()) return

        CoroutineScope(Dispatchers.Main).launch {
            progressBar.visibility = View.VISIBLE
            layoutResult.visibility = View.GONE

            try {
                val allStaff = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.getStaff()
                }

                val staff = allStaff.find { it.name.contains(nomeDigitado, ignoreCase = true) }

                if (staff != null) {
                    tvName.text      = "Nome: ${staff.name}"
                    tvAlternate.text = "Outros nomes: ${staff.alternateNames.joinToString(", ")}"
                    tvSpecies.text   = "Espécie: ${staff.species}"
                    tvHouse.text     = "Casa: ${staff.house}"
                    layoutResult.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this@StaffActivity, "Professor não encontrado", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@StaffActivity, "Erro ao consultar a API", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
