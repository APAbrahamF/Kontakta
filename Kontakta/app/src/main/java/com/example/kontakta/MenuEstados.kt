package com.example.kontakta

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MenuEstados: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_estados)
        var IDUser : String = intent.getStringExtra("IDUsuario").toString()
        val botonAgs: Button = findViewById(R.id.buttonAgs) as Button
        botonAgs.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Aguascalientes")
            startActivity(intent)
        }
        val botonBC: Button = findViewById(R.id.buttonBC) as Button
        botonBC.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Baja California")
            startActivity(intent)
        }
        val botonBCS: Button = findViewById(R.id.buttonBCS) as Button
        botonBCS.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Baja California Sur")
            startActivity(intent)
        }
        val botonCam: Button = findViewById(R.id.buttonCampeche) as Button
        botonCam.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Campeche")
            startActivity(intent)
        }
        val botonCDMX: Button = findViewById(R.id.buttonCDMX) as Button
        botonCDMX.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","CDMX")
            startActivity(intent)
        }
        val botonChia: Button = findViewById(R.id.buttonChiapas) as Button
        botonChia.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Chiapas")
            startActivity(intent)
        }
        val botonChih: Button = findViewById(R.id.buttonChihuahua) as Button
        botonChih.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Chihuahua")
            startActivity(intent)
        }
        val botonCoa: Button = findViewById(R.id.buttonCoahuila) as Button
        botonCoa.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Coahuila")
            startActivity(intent)
        }
        val botonCol: Button = findViewById(R.id.buttonColima) as Button
        botonCol.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Colima")
            startActivity(intent)
        }
        val botonDur: Button = findViewById(R.id.buttonDurango) as Button
        botonDur.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Durango")
            startActivity(intent)
        }
        val botonEdo: Button = findViewById(R.id.buttonEdomex) as Button
        botonEdo.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Estado de Mexico")
            startActivity(intent)
        }
        val botonGua: Button = findViewById(R.id.buttonGuanajuato) as Button
        botonGua.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Guanajuato")
            startActivity(intent)
        }
        val botonGue: Button = findViewById(R.id.buttonGuerrero) as Button
        botonGue.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Guerrero")
            startActivity(intent)
        }
        val botonHid: Button = findViewById(R.id.buttonHidalgo) as Button
        botonHid.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Hidalgo")
            startActivity(intent)
        }
        val botonJal: Button = findViewById(R.id.buttonJalisco) as Button
        botonJal.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Jalisco")
            startActivity(intent)
        }
        val botonMic: Button = findViewById(R.id.buttonMichoacan) as Button
        botonMic.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Michoacan")
            startActivity(intent)
        }
        val botonMor: Button = findViewById(R.id.buttonMorelos) as Button
        botonMor.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Morelos")
            startActivity(intent)
        }
        val botonNay: Button = findViewById(R.id.buttonNayarit) as Button
        botonNay.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Nayarit")
            startActivity(intent)
        }
        val botonNL: Button = findViewById(R.id.buttonNuevoLeon) as Button
        botonNL.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Nuevo Leon")
            startActivity(intent)
        }
        val botonOax: Button = findViewById(R.id.buttonOaxaca) as Button
        botonOax.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Oaxaca")
            startActivity(intent)
        }
        val botonPue: Button = findViewById(R.id.buttonPuebla) as Button
        botonPue.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Puebla")
            startActivity(intent)
        }
        val botonQue: Button = findViewById(R.id.buttonQueretaro) as Button
        botonQue.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Queretaro")
            startActivity(intent)
        }
        val botonQui: Button = findViewById(R.id.buttonQuintanaRoo) as Button
        botonQui.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Quintana Roo")
            startActivity(intent)
        }
        val botonSLP: Button = findViewById(R.id.buttonSLP) as Button
        botonSLP.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","San Luis Potosi")
            startActivity(intent)
        }
        val botonSin: Button = findViewById(R.id.buttonSinaloa) as Button
        botonSin.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Sinaloa")
            startActivity(intent)
        }
        val botonSon: Button = findViewById(R.id.buttonSonora) as Button
        botonSon.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Sonora")
            startActivity(intent)
        }
        val botonTab: Button = findViewById(R.id.buttonTabasco) as Button
        botonTab.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Tabasco")
            startActivity(intent)
        }
        val botonTam: Button = findViewById(R.id.buttonTamaulipas) as Button
        botonTam.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Tamaulipas")
            startActivity(intent)
        }
        val botonTla: Button = findViewById(R.id.buttonTlaxcala) as Button
        botonTla.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Tlaxcala")
            startActivity(intent)
        }
        val botonVer: Button = findViewById(R.id.buttonVeracruz) as Button
        botonVer.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Veracruz")
            startActivity(intent)
        }
        val botonYuc: Button = findViewById(R.id.buttonYucatan) as Button
        botonYuc.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Yucatan")
            startActivity(intent)
        }
        val botonZac: Button = findViewById(R.id.buttonZacatecas) as Button
        botonZac.setOnClickListener{
            val intent = Intent(this,vistaEstado::class.java)
            intent.putExtra("IDUsuario",IDUser)
            intent.putExtra("estado","Zacatecas")
            startActivity(intent)
        }
        val buttonInicio: ImageButton = findViewById(R.id.confInicioButt) as ImageButton
        buttonInicio.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        val buttonRecomend: ImageButton = findViewById(R.id.confRecomButt) as ImageButton
        buttonRecomend.setOnClickListener{
            val intent = Intent(this, MenuPrincipal::class.java)
            intent.putExtra("correo", correoGlobal);
            startActivity(intent)
        }
        var buttConf: ImageButton = findViewById(R.id.imageButton17) as ImageButton
        buttConf.setOnClickListener {
            val intent1 = Intent(this, MenuConfiguracion::class.java)
            intent1.putExtra("correo", correoGlobal);
            intent1.putExtra("IDUsuario", idUsuarioGlobal);
            startActivity(intent1)
        }

    }

}