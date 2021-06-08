package com.example.kontakta

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
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


class perfilServ : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_prestador)
        var IDServ : String = intent.getStringExtra("IDServicio").toString()
        var IDUser : String = intent.getStringExtra("IDUsuario").toString()
        println("IDServicio en perfilServ = $IDServ")
        getData(IDServ)
        var buttRev: Button = findViewById(R.id.buttonReview) as Button
        buttRev.setOnClickListener{
            val intent1 = Intent(this, listaReview::class.java)
            intent1.putExtra("review", IDServ);
            intent1.putExtra("IDUsuario", IDUser);
            startActivity(intent1)
        }
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
        var rating:RatingBar=findViewById(R.id.ratingServ) as RatingBar
        var promedio: TextView = findViewById(R.id.promedioServ) as TextView
        var imgCadena = "";
        //val url = "http://192.168.1.45/kontakta/v1/getServ.php"
        //val url = "http://192.168.1.109/kontakta/v1/getServ.php"
        val url = "http://192.168.100.6/v1/getServPK.php"

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
                    rating.rating=obj.getString("promedio").toFloat()
                    val promAux="Calificacion promedio: " + obj.getString("promedio")
                    promedio.setText(promAux)
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
