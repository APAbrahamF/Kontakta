package com.example.kontakta

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.registro_usuario.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var BotonEnviar: ImageButton = findViewById(R.id.Button1M) as ImageButton;
        BotonEnviar.setOnClickListener {
            val correo: EditText = findViewById(R.id.textCorreo) as EditText;
            val contraseña: EditText = findViewById(R.id.textPassword) as EditText;
            if(correo.getText().length == 0 || contraseña.getText().length == 0){
                Toast.makeText(this@MainActivity, "Alguno de los valores esta vacio", Toast.LENGTH_SHORT).show()
            }
            else
            {
                loginUser()
            }
        }
        val botonMenu: Button = findViewById(R.id.buttonPruebas) as Button
        botonMenu.setOnClickListener{
            val intent = Intent(this,Pruebas::class.java)
            startActivity(intent)
        }
        val botonPruebas: Button = findViewById(R.id.buttonMenu) as Button
        botonPruebas.setOnClickListener{
            val intent = Intent(this,MenuPrincipal::class.java)
            startActivity(intent)
        }
        val BotonRegistro: Button = findViewById(R.id.buttonRegistro) as Button;
        BotonRegistro.setOnClickListener {
            val intent = Intent(this,RegistroUsuario::class.java)
            startActivity(intent)
        }

        }

    private fun loginUser() {
        //getting the record values
        val queue = Volley.newRequestQueue(this);
        val correo = textCorreo?.text.toString()
        val password = textPassword?.text.toString()

        val url = "http://192.168.1.109/kontakta/v1/login.php"
        //val url = "http://192.168.100.6/v1/login.php"


        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                    var bandera = obj.getString("error");
                    if(bandera == "true")
                    {
                            val intent1 = Intent(this, MenuPrincipal::class.java)
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
                params.put("correo", correo)
                params.put("password", password)
                return params
            }
        }
            //adding request to queue
            queue.add(stringRequest);
    }

    }