package com.example.kontakta

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.cambio_prestador.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

var idServicio: String = ""

class CambioDatosPrestador : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cambio_prestador)
        var correo : String = intent.getStringExtra("correo").toString()
        getIDServicio(correo)
        getData(idServicio)
        val BotonGuardar: FloatingActionButton = findViewById(R.id.FABGuardarP) as FloatingActionButton;
        BotonGuardar.setOnClickListener {
            guardar();
        }
    }
    private fun getIDServicio(correo: String) {
        val queue = Volley.newRequestQueue(this);

        val url = "http://192.168.1.45/kontakta/v1/getUser.php"

        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    //Aqui es donde se jalan los datos desde la base en un jsonArray, puedes ver en el php como los traigo
                    val jsonArray=JSONArray(response)
                    //Aqui le digo que tome el raw 0 y que lo haga un jsonObject para poder usar los datos
                    val obj = JSONObject(jsonArray.getString(0))
                    //A partir de aqui solo pongo los datos que jale en los espacios del edit text
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                    idServicio = obj.getString("IDServicio_FK")
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
        var nombre: EditText = findViewById(R.id.textNombre2) as EditText
        var descripcion: EditText = findViewById(R.id.textDescripcionP) as EditText
        var integrantes: EditText = findViewById(R.id.textIntegrantesP) as EditText
        var facebook: EditText = findViewById(R.id.textFacebookP) as EditText
        var instagram: EditText = findViewById(R.id.textInstagramP) as EditText
        var youtube: EditText = findViewById(R.id.textYoutubeP) as EditText
        var genero: EditText = findViewById(R.id.editTextGenero) as EditText

        val url = "http://192.168.1.45/kontakta/v1/getServ.php"

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
                    descripcion.setText(obj.getString("descripcion"))
                    integrantes.setText(obj.getString("integrantes"))
                    genero.setText(obj.getString("genero"))
                    facebook.setText(obj.getString("facebook"))
                    instagram.setText(obj.getString("instagram"))
                    youtube.setText(obj.getString("youtube"))
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
    private fun guardar() {
        //getting the record values
        val queue = Volley.newRequestQueue(this);
        val nombreServicio = textNombre2?.text.toString()
        val descripcion = textDescripcionP?.text.toString()
        val integrantes = textIntegrantesP?.text.toString()
        val genero = editTextGenero?.text.toString()
        val facebook = textFacebookP?.text.toString()
        val youtube = textYoutubeP?.text.toString()
        val instagram = textInstagramP?.text.toString()

        val url = "http://192.168.1.45/kontakta/v1/actualizarServ.php"


        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    //El php de aqui actualiza los datos de la base por lo que solo le solicito el mensaje
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                //Aqui le paso los datos de los edittext para que los actualize
                params.put("IDServicio", idServicio)
                params.put("nombreServicio", nombreServicio)
                params.put("descripcion", descripcion)
                params.put("genero", genero)
                params.put("integrantes", integrantes)
                params.put("facebook", facebook)
                params.put("youtube", youtube)
                params.put("instagram", instagram)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }
}