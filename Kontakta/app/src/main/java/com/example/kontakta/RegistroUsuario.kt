package com.example.kontakta
import android.content.Context
import android.content.pm.ActivityInfo
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
import com.example.mysqlbd.VolleySingleton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.registro_usuario.*
import org.json.JSONException
import org.json.JSONObject

@Suppress("DEPRECATION")
class RegistroUsuario:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_usuario)
        val conexion = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val status = conexion.activeNetworkInfo

        val btnRegister: Button = findViewById(R.id.buttonRegistro) as Button;

        btnRegister.setOnClickListener() {
            addUser()
            if (status != null && status.isConnected) {
                addUser()
            } else {
                Toast.makeText(this, "Revise su conexion a internet", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun addUser() {
        //getting the record values
        val queue = Volley.newRequestQueue(this);
        val nombre = textNombre?.text.toString()
        val edad = textEdad?.text.toString()
        val sexo = textSexo?.text.toString()
        val direccion = textDireccion?.text.toString()
        val municipio = textMunicipio?.text.toString()
        val estado = textEstado?.text.toString()
        val correo = textCorreo1?.text.toString()
        val password = textPass1?.text.toString()

        val url = "http://192.168.1.109/kontakta/v1/index.php"


        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
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
                params.put("nombre", nombre)
                params.put("edad", edad)
                params.put("sexo", sexo)
                params.put("direccion", direccion)
                params.put("municipio", municipio)
                params.put("estado", estado)
                params.put("correo", correo)
                params.put("password", password)
                return params
            }
        }

        //adding request to queue
        queue.add(stringRequest);
    }

}
