package com.example.kontakta

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.*
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_cambiar_contra.*
import kotlinx.android.synthetic.main.cambio_prestador.*
import kotlinx.android.synthetic.main.cambio_usuario.*
import kotlinx.android.synthetic.main.registro_usuario.*
import kotlinx.android.synthetic.main.registro_usuario.textDireccion
import kotlinx.android.synthetic.main.registro_usuario.textEdad
import kotlinx.android.synthetic.main.registro_usuario.textEstado
import kotlinx.android.synthetic.main.registro_usuario.textMunicipio
import kotlinx.android.synthetic.main.registro_usuario.textNombre
import kotlinx.coroutines.internal.artificialFrame
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

//variable local para verificar que se conoce la contraseña actual al momento de querer modificarla

class Confirmacion : AppCompatActivity() {
    object GlobalVariableConf {
        var idUsuarioActual: String = ""
        var idServicioActual: String = ""
        var tipo: String=""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirmacion)
        var correo : String = intent.getStringExtra("correo").toString()
        var tipotemp : String = intent.getStringExtra("tipo").toString()
        GlobalVariableConf.tipo=tipotemp
        getIDUsuario(correo)

        println("==============TIPO: ${GlobalVariableConf.tipo}")
        println("==============Correo: $correo")
        println("====================================================================IDServicio: ${GlobalVariableConf.idServicioActual}")
        println("====================================================================IDUsuario: ${GlobalVariableConf.idUsuarioActual}")
    }
    private fun getIDUsuario(correo: String) {
        val queue = Volley.newRequestQueue(this);

        val url = "http://192.168.1.45/kontakta/v1/getUser.php"
        //val url = "http://192.168.1.109/kontakta/v1/getUser.php"
        //val url = "http://192.168.100.6/v1/getUser.php"

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
                    GlobalVariableConf.idUsuarioActual = obj.getString("IDUsuario")
                    println("FUNCION====================================================================IDUsuario: ${GlobalVariableConf.idUsuarioActual}")
                    if (GlobalVariableConf.tipo=="prestador")
                    {
                        val intent1 = Intent(this, Confirmacion2::class.java)
                        intent1.putExtra("correo", correo);
                        intent1.putExtra("IDUsuario", GlobalVariableConf.idUsuarioActual);
                        startActivity(intent1)
                    }
                    else{
                        val intent1 = Intent(this, MenuPrincipal::class.java)
                        intent1.putExtra("correo", correo);
                        intent1.putExtra("IDUsuario", GlobalVariableConf.idUsuarioActual);
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
                //Esta funcion es la que pone los parametros en el php, aqui le pasas lo que va a ocupar el php
                params.put("correo", correo)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }

}