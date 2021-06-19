package com.example.kontakta

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class vistaTopRated  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pruebas2)
        var correo : String = intent.getStringExtra("correo").toString()
        var IDUser : String = intent.getStringExtra("IDUsuario").toString()

        var listview = findViewById<ListView>(R.id.listView2)
        var list = mutableListOf<Model>()

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.6/v1/getTopRated.php"
        //val url = "http://192.168.1.45/kontakta/v1/serviciosGET.php"
        val stringRequest = StringRequest(Request.Method.GET,url, { response ->
            val jsonArray= JSONArray(response)
            for(i in 0 until jsonArray.length()){
                val jsonObject = JSONObject(jsonArray.getString(i))
                list.add(Model(jsonObject.get("IDServicio").toString(),jsonObject.get("nombreServicio").toString(),jsonObject.get("imagen").toString()))
            }
            listview.adapter = MyAdapter(this,R.layout.row,list)
            listview.setOnItemClickListener { parent: AdapterView<*>, view: View, position:Int, id:Long ->
                println("posicion en la lista: $position")
                println("IDServicio: "+list[position].IDUsuario)
                val intent1 = Intent(this, perfilServ::class.java)
                intent1.putExtra("IDServicio", list[position].IDUsuario);
                intent1.putExtra("IDUsuario", IDUser)
                startActivity(intent1)
            }
        }, { error ->

        })
        queue.add(stringRequest)
    }
}