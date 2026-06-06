package com.example.harrypotter.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.harrypotter.R
import com.example.harrypotter.dto.CharacterDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterActivity : AppCompatActivity() {

    private lateinit var etId: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var layoutResult: LinearLayout
    private lateinit var ivPhoto: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvSpecies: TextView
    private lateinit var tvHouse: TextView

    private val mockCharacters = listOf(
        CharacterDTO("1", "Harry Potter", "human", "Gryffindor"),
        CharacterDTO("2", "Hermione Granger", "human", "Gryffindor"),
        CharacterDTO("3", "Ron Weasley", "human", "Gryffindor"),
        CharacterDTO("4", "Albus Dumbledore", "human", "Gryffindor"),
        CharacterDTO("5", "Draco Malfoy", "human", "Slytherin"),
        CharacterDTO("6", "Luna Lovegood", "human", "Ravenclaw"),
        CharacterDTO("7", "Cedric Diggory", "human", "Hufflepuff"),
        CharacterDTO("8", "Lord Voldemort", "human", "Slytherin"),
        CharacterDTO("9", "Neville Longbottom", "human", "Gryffindor"),
        CharacterDTO("10", "Ginny Weasley", "human", "Gryffindor"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_character)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        etId         = findViewById(R.id.et_character_id)
        progressBar  = findViewById(R.id.progress_bar)
        layoutResult = findViewById(R.id.layout_result)
        ivPhoto      = findViewById(R.id.iv_character_photo)
        tvName       = findViewById(R.id.tv_character_name)
        tvSpecies    = findViewById(R.id.tv_character_species)
        tvHouse      = findViewById(R.id.tv_character_house)

        findViewById<Button>(R.id.button_voltar).setOnClickListener {
            finish()
        }
    }

    fun buscarPersonagem(view: View) {
        val id = etId.text.toString().trim()
        if (id.isEmpty()) return

        CoroutineScope(Dispatchers.Main).launch {
            progressBar.visibility = View.VISIBLE
            layoutResult.visibility = View.GONE

            val character = withContext(Dispatchers.IO) {
                // TODO: HttpClient.getCharacterById(id)
                mockCharacters.find { it.id == id } ?: mockCharacters.first()
            }

            progressBar.visibility = View.GONE

            tvName.text    = "Nome: ${character.name}"
            tvSpecies.text = "Espécie: ${character.species}"
            tvHouse.text   = "Casa: ${character.house}"
            ivPhoto.setImageResource(R.mipmap.ic_launcher)
            layoutResult.visibility = View.VISIBLE
        }
    }
}
