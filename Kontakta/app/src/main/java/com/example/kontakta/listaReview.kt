package com.example.kontakta

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class listaReview : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_review)
        var servicio : String = intent.getStringExtra("review").toString()

        var listview = findViewById<ListView>(R.id.listView_review)
        var list = mutableListOf<ModelReview>()

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.6/v1/getServReviews.php"
        //val url = "http://192.168.1.45/kontakta/v1/usuariosGET.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
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
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    },
    Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }) {
        @Throws(AuthFailureError::class)
        override fun getParams(): Map<String, String> {
            val params = HashMap<String, String>()
            //Esta funcion es la que pone los parametros en el php, aqui le pasas lo que va a ocupar el php
            params.put("IDUserFK", servicio)
            return params
        }
    }
    //adding request to queue
    queue.add(stringRequest);
    }
}