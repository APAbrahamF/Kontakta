package com.example.kontakta

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.*
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.cambio_prestador.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class vistaGenero : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pruebas2)
        var genero : String = intent.getStringExtra("genero").toString()
        var IDUser : String = intent.getStringExtra("IDUsuario").toString()
        println("INTENT en vistaGenero ====================================================IDUser: $IDUser")
        val actionBar = supportActionBar
        actionBar!!.title = genero
        getData(genero)
    }

    private fun getData(genero: String) {
        val queue = Volley.newRequestQueue(this);
        var listview = findViewById<ListView>(R.id.listView2)
        var list = mutableListOf<Model>()
        //val url = "http://192.168.1.45/kontakta/v1/getServ.php"
        //val url = "http://192.168.1.109/kontakta/v1/getServ.php"
        val url = "http://192.168.100.6/v1/generosGET.php"

        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    //Aqui es donde se jalan los datos desde la base en un jsonArray, puedes ver en el php como los traigo
                    val jsonArray= JSONArray(response)
                    //Aqui le digo que tome el raw 0 y que lo haga un jsonObject para poder usar los datos
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
                params.put("genero", genero)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }
}
