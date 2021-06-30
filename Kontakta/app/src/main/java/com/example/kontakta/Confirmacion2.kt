package com.example.kontakta

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

var idServicioActual = ""
class Confirmacion2: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirmacion2)

        var correo : String = intent.getStringExtra("correo").toString()
        var IDUser : String = intent.getStringExtra("IDUsuario").toString()
        getIDServicio(IDUser,correo)

        val btnConfirmacion: Button = findViewById(R.id.buttonConfirm) as Button
        btnConfirmacion.setOnClickListener() {
            val intent1 = Intent(this, MenuPrincipal::class.java)
            intent1.putExtra("correo", correo);
            startActivity(intent1)
        }
    }

    private fun getIDServicio(IDUser: String,correo: String) {
        val queue = Volley.newRequestQueue(this);

        val url = "http://192.168.1.45/kontakta/v1/getUser.php"
        //val url = "http://192.168.1.109/kontakta/v1/getUser.php"
        //val url = "http://192.168.100.6/v1/getServ2.php"

        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    //Aqui es donde se jalan los datos desde la base en un jsonArray, puedes ver en el php como los traigo
                    val jsonArray= JSONArray(response)
                    //Aqui le digo que tome el raw 0 y que lo haga un jsonObject para poder usar los datos
                    val obj = JSONObject(jsonArray.getString(0))
                    //println("FUNCION====================================================================IDUsuario: $idUsuarioActual")
                    //A partir de aqui solo pongo los datos que jale en los espacios del edit text
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                    idServicioActual = obj.getString("IDServicio")
                    println("FUNCION====================================================================IDServicio: $idServicioActual")
                    val intent1 = Intent(this, MenuPrincipal::class.java)
                    intent1.putExtra("IDUsuario", IDUser);
                    intent1.putExtra("IDServicio", idServicioActual);
                    intent1.putExtra("correo", correo);
                    startActivity(intent1)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                //Esta funcion es la que pone los parametros en el php, aqui le pasas lo que va a ocupar el php
                params.put("IDServ", IDUser)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }

}