package com.example.kontakta

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuEstados: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_estados)
        var IDUser : String = intent.getStringExtra("IDUsuario").toString()
        val botonJal: Button = findViewById(R.id.buttonJalisco) as Button
        botonJal.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Jalisco")
            startActivity(intent)
        }

    }

}