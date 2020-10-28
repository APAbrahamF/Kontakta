package com.example.kontakta

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CambioDatosUsuario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cambio_usuario)
        var BotonGuardar: FloatingActionButton = findViewById(R.id.FABGuardarU) as FloatingActionButton;
        BotonGuardar.setOnClickListener {
            guardar();
        }
    }
    private fun guardar(){
        //Metodo prron donde se van a guardar los cambios
    }
}