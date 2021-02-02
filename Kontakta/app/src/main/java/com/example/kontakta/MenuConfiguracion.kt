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
        var correo : String = intent.getStringExtra("correo").toString()
        buttonCam.setOnClickListener{
            val intent = Intent(this,CambioDatosUsuario::class.java)
            intent.putExtra("correo", correo);
            startActivity(intent)
        }
        val buttonCamP: Button = findViewById(R.id.buttonCServ) as Button
        buttonCamP.setOnClickListener{
            val intent = Intent(this,CambioDatosPrestador::class.java)
            intent.putExtra("correo", correo);
            startActivity(intent)
        }
        val buttonCamC: Button = findViewById(R.id.buttonCamContra) as Button
        buttonCamC.setOnClickListener{
            val intent = Intent(this,cambiar_contra::class.java)
            intent.putExtra("correo", correo);
            startActivity(intent)
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