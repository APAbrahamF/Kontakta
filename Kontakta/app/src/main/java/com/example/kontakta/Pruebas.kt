package com.example.kontakta

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
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

        var listview = findViewById<ListView>(R.id.listView)
        var list = mutableListOf<Model>()

        val queue = Volley.newRequestQueue(this)
        //val url = "http://192.168.100.6/v1/usuariosGET.php"
        val url = "http://192.168.1.45/kontakta/v1/usuariosGET.php"
        val stringRequest = StringRequest(Request.Method.GET,url, { response ->
            val jsonArray=JSONArray(response)
            for(i in 0 until jsonArray.length()){
                val jsonObject = JSONObject(jsonArray.getString(i))
                list.add(Model(jsonObject.get("IDUsuario").toString(),jsonObject.get("nombre").toString(),jsonObject.get("imagen").toString()))
            }
            listview.adapter = MyAdapter(this,R.layout.row,list)
            listview.setOnItemClickListener { parent: AdapterView<*>, view:View, position:Int, id:Long ->
                println("posicion en la lista: $position")
                println("IDUsuario: "+list[position].IDUsuario)
            }
        }, { error ->

        })
        queue.add(stringRequest)
    }
}