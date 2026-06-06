package com.example.harrypotter.activity

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.harrypotter.R

class SpellDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_spell_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        val name = intent.getStringExtra("SPELL_NAME") ?: ""
        val desc = intent.getStringExtra("SPELL_DESC") ?: ""

        findViewById<TextView>(R.id.tv_spell_name).text        = name
        findViewById<TextView>(R.id.tv_spell_description).text = desc

        findViewById<android.view.View>(R.id.btn_voltar).setOnClickListener {
            finish()
        }
    }
}
