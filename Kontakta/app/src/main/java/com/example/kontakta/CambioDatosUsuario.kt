package com.example.kontakta

import android.content.Intent
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
import kotlinx.android.synthetic.main.cambio_usuario.*
import kotlinx.android.synthetic.main.registro_prestador.*
import kotlinx.android.synthetic.main.registro_usuario.*
import kotlinx.android.synthetic.main.registro_usuario.textDireccion
import kotlinx.android.synthetic.main.registro_usuario.textEdad
import kotlinx.android.synthetic.main.registro_usuario.textEstado
import kotlinx.android.synthetic.main.registro_usuario.textMunicipio
import kotlinx.android.synthetic.main.registro_usuario.textNombre
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class CambioDatosUsuario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cambio_usuario)
        //Esta es la funcion que jala los datos actuales del usuario
        //ese correo lo tengo en la base, despues jalaremos el correo del usuario actual desde el menu
        //por el momento usa un correo que tengas tu registrado para jalar los registros
        getData("abraham1902@hotmail.com")
        //Aqui estoy jalando el boton desde la interfaz para luego crear el evento oncreate
        var BotonGuardar: FloatingActionButton = findViewById(R.id.FABGuardarU) as FloatingActionButton;
        BotonGuardar.setOnClickListener {
            //Esta es la funcion que jala datos bien chidori, igual le tienes que pasar el correo del usuario para saber donde guardar
            guardar("abraham1902@hotmail.com");
        }
    }
    private fun getData(correo: String)
    {
        //Aqui jalo los editText para poder poner ahi los datos que voy a jalar desde la base
        val queue = Volley.newRequestQueue(this);
        var nombre: EditText = findViewById(R.id.textNombre) as EditText
        var sexo: EditText = findViewById(R.id.textSexo3) as EditText
        var edad: EditText = findViewById(R.id.textEdad) as EditText
        var municipio: EditText = findViewById(R.id.textMunicipio) as EditText
        var direccion: EditText = findViewById(R.id.textDireccion) as EditText
        var estado: EditText = findViewById(R.id.textEstado) as EditText

        //Aqui va la url de tu server, usa tu ip si vas a trabajar en tu celular
        val url = "http://192.168.1.109/kontakta/v1/getUser.php"


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
                    nombre.setText(obj.getString("nombre"))
                    sexo.setText(obj.getString("sexo"))
                    edad.setText(obj.getString("edad"))
                    municipio.setText(obj.getString("municipio"))
                    direccion.setText(obj.getString("direccion"))
                    estado.setText(obj.getString("estado"))
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
    private fun guardar(correo: String) {
        //getting the record values
        //Same shit, pero estas funciones lo que hacen es jalar solo el texto de los edittext y no el edittext completo
        val queue = Volley.newRequestQueue(this);
        val nombre = textNombre?.text.toString()
        val edad = textEdad?.text.toString()
        val sexo = textSexo3?.text.toString()
        val direccion = textDireccion?.text.toString()
        val municipio = textMunicipio?.text.toString()
        val estado = textEstado?.text.toString()

        val url = "http://192.168.1.109/kontakta/v1/actualizarUser.php"


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
                params.put("nombre", nombre)
                params.put("edad", edad)
                params.put("sexo", sexo)
                params.put("direccion", direccion)
                params.put("municipio", municipio)
                params.put("estado", estado)
                params.put("correo", correo)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }
}