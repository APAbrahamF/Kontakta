package com.example.kontakta

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton

class MenuPrincipal: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_principal)
        var buttConf:ImageButton = findViewById(R.id.confButton) as ImageButton
        buttConf.setOnClickListener{
            val intent = Intent(this,MenuConfiguracion::class.java)
            startActivity(intent)
        }
    }
}