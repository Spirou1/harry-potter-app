package com.example.harrypotter

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

    private val mockStaff = listOf(
        StaffDTO("Albus Dumbledore",       listOf("Professor Dumbledore", "Albus Percival Wulfric Brian Dumbledore"), "human", "Gryffindor"),
        StaffDTO("Minerva McGonagall",     listOf("Professor McGonagall", "Minnie"),                                   "human", "Gryffindor"),
        StaffDTO("Severus Snape",          listOf("Professor Snape", "The Half-Blood Prince"),                        "human", "Slytherin"),
        StaffDTO("Rubeus Hagrid",          listOf("Hagrid"),                                                           "half-giant", "Gryffindor"),
        StaffDTO("Filius Flitwick",        listOf("Professor Flitwick"),                                               "human", "Ravenclaw"),
        StaffDTO("Pomona Sprout",          listOf("Professor Sprout"),                                                 "human", "Hufflepuff"),
        StaffDTO("Sybill Trelawney",       listOf("Professor Trelawney"),                                              "human", "Gryffindor"),
        StaffDTO("Dolores Umbridge",       listOf("Professor Umbridge", "High Inquisitor"),                           "human", "Slytherin"),
        StaffDTO("Horace Slughorn",        listOf("Professor Slughorn"),                                               "human", "Slytherin"),
        StaffDTO("Remus Lupin",            listOf("Professor Lupin", "Moony"),                                         "werewolf", "Gryffindor"),
    )

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
    }

    fun buscarProfessor(view: View) {
        val nome = etName.text.toString().trim()
        if (nome.isEmpty()) return

        CoroutineScope(Dispatchers.Main).launch {
            progressBar.visibility = View.VISIBLE
            layoutResult.visibility = View.GONE

            val staff = withContext(Dispatchers.IO) {
                // TODO: HttpClient.getStaffByName(nome)
                mockStaff.find { it.name.contains(nome, ignoreCase = true) }
                    ?: mockStaff.first()
            }

            progressBar.visibility = View.GONE

            tvName.text      = "Nome: ${staff.name}"
            tvAlternate.text = "Outros nomes: ${staff.alternateNames.joinToString()}"
            tvSpecies.text   = "Espécie: ${staff.species}"
            tvHouse.text     = "Casa: ${staff.house}"
            layoutResult.visibility = View.VISIBLE
        }
    }
}
