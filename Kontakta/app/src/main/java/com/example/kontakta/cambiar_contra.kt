package com.example.kontakta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
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
var contraActual: String = ""

class cambiar_contra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambiar_contra)
        var correo : String = intent.getStringExtra("correo").toString()
        getContraActual(correo)
        var BotonGuardar: FloatingActionButton = findViewById(R.id.FABGuardarContra) as FloatingActionButton;
        BotonGuardar.setOnClickListener {
            val contraActEditText = editTextContraAct?.text.toString()
            val contra1 = editTextContraN?.text.toString()
            val contra2 = editTextContraN2?.text.toString()
            var contra: EditText = findViewById(R.id.editTextContraAct) as EditText
            if(contra1.length == 0 || contra2.length == 0 || contraActEditText.length == 0){
                Toast.makeText(applicationContext, "Alguno de los valores esta vacio", Toast.LENGTH_LONG).show()
            } else{
                if(contra1 == contra2){
                    if(contraActEditText == contraActual){
                        guardar(correo, contra1)
                        //Se actualiza en el edittext la contraseña actual
                        contra.setText(contra1)
                        //se actualiza la variable local
                        contraActual = contra1
                    } else{
                        Toast.makeText(applicationContext, "La contraseña actual ingresada es incorrecta", Toast.LENGTH_LONG).show()
                    }
                } else{
                    Toast.makeText(applicationContext, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
                }
            }
        }
        val buttonInicio: ImageButton = findViewById(R.id.confInicioButt) as ImageButton
        buttonInicio.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        val buttonRecomend: ImageButton = findViewById(R.id.confRecomButt) as ImageButton
        buttonRecomend.setOnClickListener{
            val intent = Intent(this, MenuPrincipal::class.java)
            intent.putExtra("correo", correo);
            startActivity(intent)
        }
        var buttConf: ImageButton = findViewById(R.id.imageButton17) as ImageButton
        buttConf.setOnClickListener {
            val intent1 = Intent(this, MenuConfiguracion::class.java)
            intent1.putExtra("correo", correo);
            intent1.putExtra("IDUsuario", idUsuarioGlobal);
            startActivity(intent1)
        }
    }
    private fun getContraActual(correo: String) {
        //Aqui jalo los editText para poder poner ahi los datos que voy a jalar desde la base
        val queue = Volley.newRequestQueue(this);
        //Esto solo era para probar que estaba jalando correctamente la contraseña
        //var contra: EditText = findViewById(R.id.editTextContraAct) as EditText
        val url = "http://192.168.100.6/v1/getUser.php"
        //val url = "http://192.168.1.45/kontakta/v1/getUser.php"


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
                    //Guardo en una variable local la contraseña actual
                    contraActual = obj.getString("password")
                    //Aqui se seteaba en el edit text la contraseña actual para las pruebas
                    //contra.setText(obj.getString("password"))

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
    private fun guardar(correo: String, password: String) {
        //getting the record values
        //Same shit, pero estas funciones lo que hacen es jalar solo el texto de los edittext y no el edittext completo
        val queue = Volley.newRequestQueue(this);
        //val nombre = textNombre?.text.toString()
        val url = "http://192.168.100.6/v1/actualizarPass.php"
        //val url = "http://192.168.1.45/kontakta/v1/actualizarPass.php"

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
                params.put("correo", correo)
                params.put("password", password)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }
}