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
        val buttonCamP: Button = findViewById(R.id.buttonCServ) as Button
        buttonCamP.setOnClickListener{
            val intent = Intent(this,CambioDatosPrestador::class.java)
            startActivity(intent)
        }
        val buttonCamC: Button = findViewById(R.id.buttonCamContra) as Button
        buttonCamC.setOnClickListener{
            val intent = Intent(this,cambiar_contra::class.java)
            startActivity(intent)
        }
    }
}