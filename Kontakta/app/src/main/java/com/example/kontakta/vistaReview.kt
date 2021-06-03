package com.example.kontakta

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
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

class vistaReview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vista_review)
        var IDUserFK : String = intent.getStringExtra("IDUsuario_FK").toString()
        var comentario : String = intent.getStringExtra("comentario").toString()
        var valoracion : String = intent.getStringExtra("valoracion").toString()
        var cantidad : String = intent.getStringExtra("cantidad").toString()
        var sumatoria : String = intent.getStringExtra("sumatoria").toString()

        println("=================================================================IDUsuario en vistaReview = $IDUserFK")
        println("=================================================================CANTIDAD en vistaReview = $cantidad")
        println("=================================================================SUMATORIA en vistaReview = $sumatoria")
        getData(IDUserFK,comentario,valoracion,cantidad,sumatoria)
    }
    private fun getData(IDUserFK: String,comentario: String,valoracion: String,cantidad: String,sumatoria: String) {
        val queue = Volley.newRequestQueue(this);
        var nombre: TextView = findViewById(R.id.nombreReview) as TextView
        var valoracionSet: RatingBar = findViewById(R.id.ratingReview) as RatingBar
        var comentarioSet: TextView = findViewById(R.id.textViewReview) as TextView
        var imageview: ImageView = findViewById(R.id.imageReview) as ImageView
        var imgCadena = "";
        comentarioSet.text=comentario
        valoracionSet.rating=valoracion.toFloat()
        var promedio=sumatoria.toFloat()/cantidad.toFloat()
        println("=================================================================PROMEDIO en vistaReview = $promedio")

        //val url = "http://192.168.1.45/kontakta/v1/getServ.php"
        //val url = "http://192.168.1.109/kontakta/v1/getServ.php"
        val url = "http://192.168.100.6/v1/getUserReview.php"

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
                    nombre.setText(obj.getString("nombre"))
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
                params.put("IDUserFK", IDUserFK)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }
}