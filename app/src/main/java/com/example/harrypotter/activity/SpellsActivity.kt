package com.example.harrypotter.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotter.R
import com.example.harrypotter.adapter.SpellAdapter
import com.example.harrypotter.api.RetrofitClient
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

        findViewById<Button>(R.id.btn_voltar_spells).setOnClickListener {
            finish()
        }
    }

    private fun carregarFeiticos() {
        CoroutineScope(Dispatchers.Main).launch {
            progressBar.visibility = View.VISIBLE

            val spells = withContext(Dispatchers.IO) {
                RetrofitClient.apiService.getSpells()
            }
            adapter.updateData(spells)

            progressBar.visibility = View.GONE
            adapter.updateData(spells)
        }
    }
}
