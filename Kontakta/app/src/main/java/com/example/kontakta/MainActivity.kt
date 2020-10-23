package com.example.kontakta

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var BotonEnviar: ImageButton = findViewById(R.id.Button1M) as ImageButton;
        BotonEnviar.setOnClickListener {
            val correo: EditText = findViewById(R.id.textCorreo) as EditText;
            val contraseña: EditText = findViewById(R.id.textPassword) as EditText;
            if(correo.getText().length == 0 || contraseña.getText().length == 0){
                Toast.makeText(this@MainActivity, "Alguno de los valores esta vacio", Toast.LENGTH_SHORT).show()
            }
            else
            {

            }
        }
        val BotonRegistro: Button = findViewById(R.id.buttonRegistro) as Button;
        BotonRegistro.setOnClickListener {
            val intent = Intent(this,RegistroUsuario::class.java)
            startActivity(intent)
        }

        }

    }