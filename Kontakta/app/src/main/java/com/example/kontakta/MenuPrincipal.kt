package com.example.kontakta

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.menu_principal.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

var idUsuarioGlobal: String = ""
class MenuPrincipal: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_principal)

        var IDUser: String = intent.getStringExtra("IDUsuario").toString()
        var IDServ: String = intent.getStringExtra("IDServicio").toString()
        var correo: String = intent.getStringExtra("correo").toString()
        var checkLogin: String = intent.getStringExtra("login").toString()

        if (checkLogin == "si") {
            getIDUsuario(correo)
            IDUser = idUsuarioGlobal
        } else {
            setFK(IDUser, IDServ)
        }
        println("INTENT==============Correo: $correo")
        println("INTENT====================================================================IDServicio: $IDServ")
        println("INTENT====================================================================IDUsuario: $IDUser")

        var buttConf: ImageButton = findViewById(R.id.confButton) as ImageButton
        buttConf.setOnClickListener {
            val intent1 = Intent(this, MenuConfiguracion::class.java)
            intent1.putExtra("correo", correo);
            startActivity(intent1)
        }
        var buttRecom: ImageButton = findViewById(R.id.buttRecomended) as ImageButton
        buttRecom.setOnClickListener {
            val intent1 = Intent(this, MBusquedaRecomendada::class.java)
            //intent1.putExtra("correo", correo);
            startActivity(intent1)
        }
        /*var cv=findViewById(R.id.card_view) as CardView
        cv.setOnClickListener{
            val intent1 = Intent(this, Pruebas2::class.java)
            intent1.putExtra("correo", correo);
            startActivity(intent1)
        }*/
        listaGeneros(IDUser)
    }

    private fun listaGeneros(IDUser: String){   val arrayList = ArrayList<ModelCard>()
    arrayList.add(ModelCard("Alternativa"))
    arrayList.add(ModelCard("Banda"))
    arrayList.add(ModelCard("Blues"))
    arrayList.add(ModelCard("Country"))
    arrayList.add(ModelCard("Electronica"))
    arrayList.add(ModelCard("Folk"))
    arrayList.add(ModelCard("Hip Hop"))
    arrayList.add(ModelCard("Jazz"))
    arrayList.add(ModelCard("Mariachi"))
    arrayList.add(ModelCard("Metal"))
    arrayList.add(ModelCard("Pop"))
    arrayList.add(ModelCard("Rock"))

    println("====================================================================IDUsuarioGLOBAL: $IDUser")
    val myAdapterCard = MyAdapterCard(arrayList, this, IDUser)
    recyclerView.layoutManager=LinearLayoutManager(this)
    recyclerView.adapter=myAdapterCard
}

    private fun setFK(IDUser: String,IDServ: String) {
        //getting the record values
        //Same shit, pero estas funciones lo que hacen es jalar solo el texto de los edittext y no el edittext completo
        val queue = Volley.newRequestQueue(this);
        //val nombre = textNombre?.text.toString()

        val url = "http://192.168.100.6/v1/actualizarLlave.php"

        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {

                    //El php de aqui actualiza los datos de la base por lo que solo le solicito el mensaje
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
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
                params.put("IDUsuario", IDUser)//IDUser)
                params.put("IDServicio_FK", IDServ)//idServicioActual)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }

    private fun getIDUsuario(correo: String) {
        val queue = Volley.newRequestQueue(this);

        //val url = "http://192.168.1.45/kontakta/v1/getUser.php"
        //val url = "http://192.168.1.109/kontakta/v1/getUser.php"
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
                    idUsuarioGlobal = obj.getString("IDUsuario")
                    println("FUNCION====================================================================IDUsuario: $idUsuarioGlobal")
                    listaGeneros(idUsuarioGlobal)
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