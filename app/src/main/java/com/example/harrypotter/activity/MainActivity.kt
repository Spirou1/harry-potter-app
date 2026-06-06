package com.example.harrypotter.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.harrypotter.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
    }

    fun abrirPersonagem(view: View) =
        startActivity(Intent(this, CharacterActivity::class.java))

    fun abrirProfessor(view: View) =
        startActivity(Intent(this, StaffActivity::class.java))

    fun abrirEstudantes(view: View) =
        startActivity(Intent(this, StudentsActivity::class.java))

    fun abrirFeiticos(view: View) =
        startActivity(Intent(this, SpellsActivity::class.java))

    fun sair(view: View) = finish()
}
