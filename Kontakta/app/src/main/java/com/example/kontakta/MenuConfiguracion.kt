package com.example.kontakta

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuConfiguracion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configuracion)
        val buttonCam: Button = findViewById(R.id.buttonCUser) as Button
        buttonCam.setOnClickListener{
            val intent = Intent(this,CambioDatosUsuario::class.java)
            startActivity(intent)
        }
    }
}