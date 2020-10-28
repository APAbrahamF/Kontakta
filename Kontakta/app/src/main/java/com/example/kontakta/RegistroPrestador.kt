package com.example.kontakta

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.registro_prestador.*
import org.json.JSONException
import org.json.JSONObject


@Suppress("DEPRECATION")
class RegistroPrestador:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_prestador)
        val conexion = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val status = conexion.activeNetworkInfo

        val btnRegister: Button = findViewById(R.id.buttonRegistroServ) as Button;

        
        var correo : String = intent.getStringExtra("correo").toString()
        btnRegister.setOnClickListener() {

            if (status != null && status.isConnected) {
                addPres(correo)
            } else {
                Toast.makeText(this, "Revise su conexion a internet", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun addPres(correo: String) {
        //getting the record values
        val queue = Volley.newRequestQueue(this);
        val nombre = textNombreServ?.text.toString()
        val integrantes = textIntegrantes?.text.toString()
        val descripcion = textDescripcion?.text.toString()
        val genero = textGenero?.text.toString()
        val youtube = textYoutube?.text.toString()
        val instagram = textInstagram?.text.toString()
        val facebook = textFacebook?.text.toString()
        val twitter = textTwitter?.text.toString()

        val url = "http://192.168.1.109/kontakta/v1/insertM.php"


        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                    var bandera = obj.getString("error");
                    if(bandera == "false")
                    {
                        val intent = Intent(this,MenuPrincipal::class.java)
                        startActivity(intent)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("nombre", nombre)
                params.put("integrantes", integrantes)
                params.put("descripcion", descripcion)
                params.put("genero", genero)
                params.put("youtube", youtube)
                params.put("instagram", instagram)
                params.put("facebook", facebook)
                params.put("twitter", twitter)
                params.put("correo", correo)
                return params
            }
        }
            //adding request to queue
            queue.add(stringRequest);
    }
}
