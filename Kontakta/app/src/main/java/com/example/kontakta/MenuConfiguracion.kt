package com.example.kontakta

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MenuConfiguracion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configuracion)
        val buttonCam: Button = findViewById(R.id.buttonCUser) as Button
        var correo : String = intent.getStringExtra("correo").toString()
        var IDUser : String = intent.getStringExtra("IDUsuario").toString()
        val buttonInicio: ImageButton = findViewById(R.id.confInicioButt) as ImageButton
        buttonInicio.setOnClickListener{
            val intent = Intent(this,MenuPrincipal::class.java)
            intent.putExtra("correo", correo);
            startActivity(intent)
        }
        val buttonRecomend: ImageButton = findViewById(R.id.confRecomButt) as ImageButton
        buttonRecomend.setOnClickListener{
            val intent = Intent(this, MBusquedaRecomendada::class.java)
            intent.putExtra("correo", correo);
            startActivity(intent)
        }
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
        val buttonHistorial: Button = findViewById(R.id.buttonHistorial) as Button
        buttonHistorial.setOnClickListener{
            val intent = Intent(this,vistaHistorial::class.java)
            intent.putExtra("IDUsuario", IDUser);
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