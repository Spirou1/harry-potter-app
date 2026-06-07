package com.example.harrypotter.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.android.material.card.MaterialCardView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.harrypotter.R
import com.example.harrypotter.api.RetrofitClient
import com.example.harrypotter.dto.CharacterDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterActivity : AppCompatActivity() {

    private lateinit var etId: TextInputEditText
    private lateinit var progressBar: ProgressBar
    private lateinit var layoutResult: MaterialCardView
    private lateinit var ivPhoto: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvSpecies: TextView
    private lateinit var tvHouse: TextView

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
        val idDigitado = etId.text.toString().trim()
        if (idDigitado.isEmpty()) {
            Toast.makeText(this, "Por favor, digite um ID para buscar", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            progressBar.visibility = View.VISIBLE
            layoutResult.visibility = View.GONE

            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.getCharacterById(idDigitado)
                }
                val character = response.firstOrNull()

                if (character != null) {
                    tvName.text = "Nome: ${character.name}"
                    tvSpecies.text = "Espécie: ${character.species}"
                    tvHouse.text = "Casa: ${character.house}"

                    if (character.image.isNotEmpty()) {
                        com.squareup.picasso.Picasso.get()
                            .load(character.image)
                            .placeholder(R.mipmap.ic_launcher)
                            .into(ivPhoto)
                    } else {
                        ivPhoto.setImageResource(R.mipmap.ic_launcher)
                    }
                    layoutResult.visibility = View.VISIBLE
                } else {
                    Toast.makeText(
                        this@CharacterActivity,
                        "Personagem não encontrado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@CharacterActivity, "Erro na busca", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
