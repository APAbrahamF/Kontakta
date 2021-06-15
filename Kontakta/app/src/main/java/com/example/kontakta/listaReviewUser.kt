package com.example.kontakta

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class listaReviewUser : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pruebas2)
        var IDUser : String = intent.getStringExtra("IDUsuario").toString()

        var listview = findViewById<ListView>(R.id.listView2)
        var list = mutableListOf<ModelReview>()

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.6/v1/getUserReviews.php"
        //val url = "http://192.168.1.45/kontakta/v1/usuariosGET.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val jsonArray= JSONArray(response)
                    for(i in 0 until jsonArray.length()){
                        val jsonObject = JSONObject(jsonArray.getString(i))
                        list.add(ModelReview(jsonObject.get("IDServicio_FK").toString(),jsonObject.get("comentario").toString(),jsonObject.get("valoracion").toString(),jsonObject.get("IDReview").toString()))
                    }
                    listview.adapter = MyAdapterReview(this,R.layout.row_review,list)
                    listview.setOnItemClickListener { parent: AdapterView<*>, view: View, position:Int, id:Long ->
                        println("posicion en la lista: $position")
                        println("IDUsuario_FK: "+list[position].IDUsuario_FK)
                        val intent1 = Intent(this, perfilServ::class.java)
                        intent1.putExtra("IDServicio", list[position].IDReview);
                        intent1.putExtra("IDUsuario", IDUser);
                        startActivity(intent1)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                //Esta funcion es la que pone los parametros en el php, aqui le pasas lo que va a ocupar el php
                params.put("IDUserFK", IDUser)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }
}