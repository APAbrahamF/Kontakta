package com.example.kontakta

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CambioDatosPrestador : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cambio_prestador)
        var BotonGuardar: FloatingActionButton = findViewById(R.id.FABGuardarP) as FloatingActionButton;
        BotonGuardar.setOnClickListener {
            guardar();
        }
    }
    private fun guardar(){
        //Metodo prron donde se van a guardar los cambios
    }
}