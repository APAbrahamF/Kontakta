package com.example.kontakta

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.menu_principal.*
import org.json.JSONException
import org.json.JSONObject

class ActualizarPromedio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirmacion2)
        var IDServ : String = intent.getStringExtra("IDServicio").toString()
        var IDUser : String = intent.getStringExtra("IDUsuario").toString()
        var Promedio : String = intent.getStringExtra("Promedio").toString()
        Toast.makeText(this, IDServ, Toast.LENGTH_LONG).show()
        setPromedio(IDServ,Promedio)

        val btonConfirmacion: Button = findViewById(R.id.buttonConfirm) as Button
        btonConfirmacion.setOnClickListener() {
            val intent1 = Intent(this, perfilServ::class.java)
            intent1.putExtra("IDServicio", IDServ );
            intent1.putExtra("IDUsuario", IDUser );
            startActivity(intent1)
        }
    }

    private fun setPromedio(IDServ: String,Promedio: String) {
        //getting the record values
        //Same shit, pero estas funciones lo que hacen es jalar solo el texto de los edittext y no el edittext completo
        val queue = Volley.newRequestQueue(this);
        //val nombre = textNombre?.text.toString()

        val url = "http://192.168.100.6/v1/actualizarPromedio.php"

        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {

                    //El php de aqui actualiza los datos de la base por lo que solo le solicito el mensaje
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                    var bandera = obj.getString("error");
                    if(bandera == "false")
                    {
                        val intent1 = Intent(this, MenuPrincipal::class.java)
                        //intent1.putExtra("IDServ", IDServ);
                        startActivity(intent1)
                    }
                    //println("TRY====================================================================IDUsuario: $IDUser")
                    //println("TRY====================================================================IDServicio: $idServicioActual")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                Toast.makeText(
                    applicationContext,
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                //Aqui le paso los datos de los edittext para que los actualize
                params.put("IDServicio", IDServ)//IDUser)
                params.put("promedio", Promedio)//idServicioActual)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }
}