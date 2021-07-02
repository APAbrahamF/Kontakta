package com.example.kontakta

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class perfilUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_usuario)
        var IDUser : String = intent.getStringExtra("IDUsuario").toString()
        getData(IDUser)
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
    private fun getData(IDUser: String) {
        //Aqui jalo los editText para poder poner ahi los datos que voy a jalar desde la base
        val queue = Volley.newRequestQueue(this);
        var nombre: TextView = findViewById(R.id.nombreUper) as TextView
        var sexo: TextView = findViewById(R.id.sexoUper) as TextView
        var edad: TextView = findViewById(R.id.edadUper) as TextView
        var ubicacion: TextView = findViewById(R.id.ubiUper) as TextView
        var correo: TextView = findViewById(R.id.correoUper) as TextView
        var imageview: ImageView = findViewById(R.id.imgUper) as ImageView
        var imgCadena = ""

        //Aqui va la url de tu server, usa tu ip si vas a trabajar en tu celular
        //IP abraham
        //val url = "http://192.168.1.109/kontakta/v1/getUser.php"
        //IP Axel
        //val url = "http://192.168.1.45/kontakta/v1/getUser.php"
        //IP p8
        val url = "https://kontatkadb.000webhostapp.com/kontakta/v1/getUserPK.php"


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
                    //Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                    nombre.setText(obj.getString("nombre"))
                    sexo.setText(obj.getString("sexo"))
                    edad.setText(obj.getString("edad") + " años")
                    val municipioAux = obj.getString("municipio")
                    val estadoAux = obj.getString("estado")
                    ubicacion.setText(municipioAux + ", " + estadoAux)
                    correo.setText(obj.getString("correo"))
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
                params.put("IDUsuario", IDUser)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }
}  