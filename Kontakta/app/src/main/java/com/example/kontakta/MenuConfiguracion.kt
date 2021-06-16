package com.example.kontakta

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.util.Base64
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
var checkConf:String=""
var idServicioConf:String=""
class MenuConfiguracion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configuracion)
        val buttonCam: Button = findViewById(R.id.buttonCUser) as Button
        var correo : String = intent.getStringExtra("correo").toString()
        var IDUser : String = intent.getStringExtra("IDUsuario").toString()
        println("====================================IDUSER ENN CONFFF: $IDUser")

        val buttonInicio: ImageButton = findViewById(R.id.confInicioButt) as ImageButton
        buttonInicio.setOnClickListener{
            val intent = Intent(this,MenuPrincipal::class.java)
            intent.putExtra("correo", correo);
            startActivity(intent)
        }
        val buttonRecomend: ImageButton = findViewById(R.id.confRecomButt) as ImageButton
        buttonRecomend.setOnClickListener{
            val intent = Intent(this, MBusquedaRecomendada::class.java)
            intent.putExtra("correo", correo);
            startActivity(intent)
        }
        buttonCam.setOnClickListener{
            val intent = Intent(this,CambioDatosUsuario::class.java)
            intent.putExtra("correo", correo);
            startActivity(intent)
        }
        val buttonCamP: Button = findViewById(R.id.buttonCServ) as Button
        buttonCamP.setOnClickListener{
            val intent = Intent(this,CambioDatosPrestador::class.java)
            intent.putExtra("correo", correo);
            startActivity(intent)
        }
        val buttonCamC: Button = findViewById(R.id.buttonCamContra) as Button
        buttonCamC.setOnClickListener{
            val intent = Intent(this,cambiar_contra::class.java)
            intent.putExtra("correo", correo);
            startActivity(intent)
        }
        val buttonHistorial: Button = findViewById(R.id.buttonHistorial) as Button
        buttonHistorial.setOnClickListener{
            val intent = Intent(this,vistaHistorial::class.java)
            intent.putExtra("IDUsuario", IDUser);
            startActivity(intent)
        }
        val buttonBorrarServ: Button = findViewById(R.id.buttonBorrarServ) as Button
        buttonBorrarServ.setOnClickListener{
            checkConf="no"
            getData(correo,IDUser)
            //nullSetUser(IDUser)

        }
        val buttonBorrarUser: Button = findViewById(R.id.buttonBorrarUser) as Button
        buttonBorrarUser.setOnClickListener{
            checkConf="si"
            getData(correo,IDUser)
            //nullSetUser(IDUser)

        }
        val buttonRegistroNuevo: Button = findViewById(R.id.buttonRegistroNuevo) as Button
        buttonRegistroNuevo.setOnClickListener{
            val intent1 = Intent(this, RegistroPrestador::class.java)
            intent1.putExtra("correo", correo);
            startActivity(intent1)
        }
        val buttonVerReviews: Button = findViewById(R.id.buttonVerReviews) as Button
        buttonVerReviews.setOnClickListener{
            val intent1 = Intent(this, listaReviewUser::class.java)
            intent1.putExtra("IDUsuario", IDUser);
            startActivity(intent1)
        }
        var buttUPerfil: Button = findViewById(R.id.testVerU) as Button
        buttUPerfil.setOnClickListener{
            val intent1 = Intent(this, perfilUsuario::class.java)
            intent1.putExtra("IDUsuario", IDUser);
            startActivity(intent1)
        }
        var buttPPerfil: Button = findViewById(R.id.testVerP) as Button
        buttPPerfil.setOnClickListener{
            val intent1 = Intent(this, perfilPrestador::class.java)
            intent1.putExtra("correo", correo);
            startActivity(intent1)
        }
    }

    private fun nullSetUser(IDUser: String) {
        val queue = Volley.newRequestQueue(this);
        var listview = findViewById<ListView>(R.id.listViewHistorial)
        var list = mutableListOf<Model>()
        //val url = "http://192.168.1.45/kontakta/v1/getServ.php"
        //val url = "http://192.168.1.109/kontakta/v1/getServ.php"
        val url = "http://192.168.100.6/v1/nullSetUser.php"

        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {

                    deleteServicio(IDUser)


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

    private fun deleteServicio(IDUser: String) {
        val queue = Volley.newRequestQueue(this);
        var listview = findViewById<ListView>(R.id.listViewHistorial)
        var list = mutableListOf<Model>()
        //val url = "http://192.168.1.45/kontakta/v1/getServ.php"
        //val url = "http://192.168.1.109/kontakta/v1/getServ.php"
        val url = "http://192.168.100.6/v1/borrarServ.php"

        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                        if(checkConf=="si"){
                            deleteUser(IDUser)
                        }else {
                            val intent1 = Intent(this, MenuPrincipal::class.java)
                            intent1.putExtra("IDUsuario", IDUser);
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
                params.put("IDUsuario_FK", IDUser)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }

    private fun deleteUser(IDUser: String) {
        val queue = Volley.newRequestQueue(this);
        var listview = findViewById<ListView>(R.id.listViewHistorial)
        var list = mutableListOf<Model>()
        //val url = "http://192.168.1.45/kontakta/v1/getServ.php"
        //val url = "http://192.168.1.109/kontakta/v1/getServ.php"
        val url = "http://192.168.100.6/v1/borrarUser.php"

        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {

                    val intent1 = Intent(this, MainActivity::class.java)
                    //intent1.putExtra("IDUsuario", IDUser);
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
                params.put("IDUsuario", IDUser)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }

    private fun getData(correo: String,IDUser: String) {
        //Aqui jalo los editText para poder poner ahi los datos que voy a jalar desde la base
        val queue = Volley.newRequestQueue(this);

        //Aqui va la url de tu server, usa tu ip si vas a trabajar en tu celular
        //IP abraham
        //val url = "http://192.168.1.109/kontakta/v1/getUser.php"
        //IP Axel
        //val url = "http://192.168.1.45/kontakta/v1/getUser.php"
        //IP p8
        val url = "http://192.168.100.6/v1/getUser.php"


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
                    println("IDSERVICIO ==============================================================================================" + obj.getString("IDServicio_FK"))
                    if(obj.getString("IDServicio_FK")=="" || obj.getString("IDServicio_FK").contains("0")){
                        deleteUser(IDUser)
                    }
                    else{
                        nullSetUser(IDUser)
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