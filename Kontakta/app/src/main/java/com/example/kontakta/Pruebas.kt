package com.example.kontakta

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.pruebas.*
import kotlinx.android.synthetic.main.row.*
import org.json.JSONArray
import org.json.JSONObject

class Pruebas : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pruebas)
        var servicio : String = intent.getStringExtra("review").toString()
        val actionBar = supportActionBar
        actionBar!!.title = servicio

        var listview = findViewById<ListView>(R.id.listView)
        var list = mutableListOf<Model>()

        val queue = Volley.newRequestQueue(this)
        //val url = "http://192.168.100.6/v1/usuariosGET.php"
        val url = "https://kontatkadb.000webhostapp.com/kontakta/v1/usuariosGET.php"
        val stringRequest = StringRequest(Request.Method.GET,url, { response ->
            val jsonArray=JSONArray(response)
            for(i in 0 until jsonArray.length()){
                val jsonObject = JSONObject(jsonArray.getString(i))
                list.add(Model(jsonObject.get("IDUsuario").toString(),jsonObject.get("correo").toString(),jsonObject.get("imagen").toString()))
            }
            listview.adapter = MyAdapter(this,R.layout.row,list)
            listview.setOnItemClickListener { parent: AdapterView<*>, view:View, position:Int, id:Long ->
                println("posicion en la lista: $position")
                println("IDUsuario: "+list[position].IDUsuario)
                val intent1 = Intent(this, perfilUsuario::class.java)
                intent1.putExtra("correo", list[position].correo);
                startActivity(intent1)
            }
        }, { error ->

        })
        queue.add(stringRequest)

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