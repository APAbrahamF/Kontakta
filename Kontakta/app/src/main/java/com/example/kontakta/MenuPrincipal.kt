package com.example.kontakta

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton

class MenuPrincipal: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_principal)
        var correo : String = intent.getStringExtra("correo").toString()
        var buttConf:ImageButton = findViewById(R.id.confButton) as ImageButton
        buttConf.setOnClickListener{
            val intent1 = Intent(this, MenuConfiguracion::class.java)
            intent1.putExtra("correo", correo);
            startActivity(intent1)
        }
    }
}