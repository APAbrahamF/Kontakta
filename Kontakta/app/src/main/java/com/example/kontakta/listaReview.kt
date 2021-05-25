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

class listaReview : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_review)
        var servicio : String = intent.getStringExtra("review").toString()

        var listview = findViewById<ListView>(R.id.listView_review)
        var list = mutableListOf<ModelReview>()

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.6/v1/reviewsGET.php"
        //val url = "http://192.168.1.45/kontakta/v1/usuariosGET.php"
        val stringRequest = StringRequest(Request.Method.GET,url, { response ->
            val jsonArray= JSONArray(response)
            for(i in 0 until jsonArray.length()){
                val jsonObject = JSONObject(jsonArray.getString(i))
                list.add(ModelReview(jsonObject.get("IDReview").toString(),jsonObject.get("comentario").toString(),jsonObject.get("valoracion").toString(),jsonObject.get("IDUsuario_FK").toString()))
            }
            listview.adapter = MyAdapterReview(this,R.layout.row_review,list)
            listview.setOnItemClickListener { parent: AdapterView<*>, view: View, position:Int, id:Long ->
                println("posicion en la lista: $position")
                println("IDUsuario_FK: "+list[position].IDUsuario_FK)
                val intent1 = Intent(this, vistaReview::class.java)
                intent1.putExtra("IDUsuario_FK", list[position].IDUsuario_FK);
                intent1.putExtra("comentario", list[position].comentario);
                intent1.putExtra("valoracion", list[position].valoracion);
                startActivity(intent1)
            }
        }, { error ->

        })
        queue.add(stringRequest)
    }
}