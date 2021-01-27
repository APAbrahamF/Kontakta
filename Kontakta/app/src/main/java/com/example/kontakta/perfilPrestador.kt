package com.example.kontakta

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.cambio_prestador.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

var idServicio2: String = ""

class perfilPrestador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_prestador)
        var correo : String = intent.getStringExtra("correo").toString()
        getIDServicio(correo)
        getData(idServicio2)
    }
    private fun getIDServicio(correo: String) {
        val queue = Volley.newRequestQueue(this);

        //val url = "http://192.168.1.45/kontakta/v1/getUser.php"
        //val url = "http://192.168.1.109/kontakta/v1/getUser.php"
        val url = "http://192.168.100.6/v1/getUser.php"

        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    //Aqui es donde se jalan los datos desde la base en un jsonArray, puedes ver en el php como los traigo
                    val jsonArray= JSONArray(response)
                    //Aqui le digo que tome el raw 0 y que lo haga un jsonObject para poder usar los datos
                    val obj = JSONObject(jsonArray.getString(0))
                    //A partir de aqui solo pongo los datos que jale en los espacios del edit text
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                    idServicio2 = obj.getString("IDUsuario")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                //Esta funcion es la que pone los parametros en el php, aqui le pasas lo que va a ocupar el php
                params.put("correo", correo)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }
    private fun getData(IDServ: String) {
        val queue = Volley.newRequestQueue(this);
        var nombre: TextView = findViewById(R.id.nombrePper) as TextView
        var descripcion: TextView = findViewById(R.id.descPper) as TextView
        var integrantes: TextView = findViewById(R.id.intePper) as TextView
        var facebook: TextView = findViewById(R.id.facePper) as TextView
        var instagram: TextView = findViewById(R.id.instaPper) as TextView
        var youtube: TextView = findViewById(R.id.youtPper) as TextView
        var twitter: TextView = findViewById(R.id.twitPper) as TextView
        var genero: TextView = findViewById(R.id.generoPper) as TextView
        var imageview: ImageView = findViewById(R.id.imgPper) as ImageView
        var imgCadena = "";

        //val url = "http://192.168.1.45/kontakta/v1/getServ.php"
        //val url = "http://192.168.1.109/kontakta/v1/getServ.php"
        val url = "http://192.168.100.6/v1/getServ.php"

        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    //Aqui es donde se jalan los datos desde la base en un jsonArray, puedes ver en el php como los traigo
                    val jsonArray= JSONArray(response)
                    //Aqui le digo que tome el raw 0 y que lo haga un jsonObject para poder usar los datos
                    val obj = JSONObject(jsonArray.getString(0))
                    //A partir de aqui solo pongo los datos que jale en los espacios del edit text
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                    nombre.setText(obj.getString("nombreServicio"))
                    val desAux = "Descripción: " + "\n" + obj.getString("descripcion")
                    descripcion.setText(desAux)
                    val inteAux = "Integrantes: " + "\n" + obj.getString("integrantes")
                    integrantes.setText(inteAux)
                    val genAux = "Género: " + "\n" + obj.getString("genero")
                    genero.setText(genAux)
                    val redesAux = "Redes sociales: " + "\n" + obj.getString("facebook")
                    facebook.setText(redesAux)
                    instagram.setText(obj.getString("instagram"))
                    youtube.setText(obj.getString("youtube"))
                    twitter.setText(obj.getString("twitter"))
                    imgCadena = obj.getString("imagen")
                    var extension = imgCadena
                    if(imgCadena.contains(","))
                        extension = extension.substringAfter(delimiter = ",", missingDelimiterValue = "Extension Not found")
                    //val extension = mItem.img.substringAfter(delimiter = ",", missingDelimiterValue = "Extension Not found")
                    val imageBytes = Base64.decode(extension, Base64.DEFAULT)
                    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    imageview.setImageBitmap(decodedImage)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                //Esta funcion es la que pone los parametros en el php, aqui le pasas lo que va a ocupar el php
                params.put("IDServ", IDServ)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }
}
