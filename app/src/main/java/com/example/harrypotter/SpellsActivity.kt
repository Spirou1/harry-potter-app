package com.example.harrypotter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SpellsActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SpellAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_spells)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        progressBar  = findViewById(R.id.progress_bar)
        recyclerView = findViewById(R.id.rv_spells)

        adapter = SpellAdapter(emptyList()) { spell ->
            val intent = Intent(this, SpellDetailActivity::class.java).apply {
                putExtra("SPELL_NAME", spell.name)
                putExtra("SPELL_DESC", spell.description)
            }
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter

        carregarFeiticos()
    }

    private fun carregarFeiticos() {
        CoroutineScope(Dispatchers.Main).launch {
            progressBar.visibility = View.VISIBLE

            val spells = withContext(Dispatchers.IO) {
                // TODO: HttpClient.getAllSpells()
                listOf(
                    SpellDTO("Alohomora",       "Opens locked doors and windows"),
                    SpellDTO("Expelliarmus",     "Disarms the opponent, knocking their wand away"),
                    SpellDTO("Lumos",            "Creates a beam of light from the tip of the wand"),
                    SpellDTO("Nox",              "Extinguishes the light produced by Lumos"),
                    SpellDTO("Wingardium Leviosa","Levitates objects into the air"),
                    SpellDTO("Expecto Patronum", "Conjures a Patronus to repel Dementors"),
                    SpellDTO("Avada Kedavra",    "Causes instant death — one of the three Unforgivable Curses"),
                    SpellDTO("Crucio",           "Inflicts intense pain on the victim"),
                    SpellDTO("Imperio",          "Places the victim under the caster's complete control"),
                    SpellDTO("Accio",            "Summons an object to the caster"),
                    SpellDTO("Reparo",           "Repairs broken or damaged objects"),
                    SpellDTO("Obliviate",        "Erases specific memories from the victim's mind"),
                    SpellDTO("Stupefy",          "Stuns the target, rendering them unconscious"),
                    SpellDTO("Finite Incantatem","Ends the effects of other spells"),
                    SpellDTO("Protego",          "Creates a magical barrier that reflects spells")
                )
            }

            progressBar.visibility = View.GONE
            adapter.updateData(spells)
        }
    }
}
