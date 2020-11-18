package com.example.kontakta

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
        var buttUPerfil: Button = findViewById(R.id.testVerU) as Button
        buttUPerfil.setOnClickListener{
            val intent1 = Intent(this, perfilUsuario::class.java)
            intent1.putExtra("correo", correo);
            startActivity(intent1)
        }
        var buttPPerfil: Button = findViewById(R.id.testVerP) as Button
        buttPPerfil.setOnClickListener{
            val intent1 = Intent(this, perfilPrestador::class.java)
            intent1.putExtra("correo", correo);
            startActivity(intent1)
        }
    }
}