package com.example.kontakta

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
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

class vistaHistorial : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vista_historial)
        var IDUser : String = intent.getStringExtra("IDUsuario").toString()
        //getData(IDServ,IDUser)
        println("====================================IDUSER ENN HISTORIAL: $IDUser")

        getData(IDUser)

        var buttBorrar: Button = findViewById(R.id.buttonBorrar) as Button
        buttBorrar.setOnClickListener {
            val intent1 = Intent(this, borrarHistorial::class.java)
            intent1.putExtra("IDUsuario", IDUser);
            startActivity(intent1)
        }
        val buttonInicio: ImageButton = findViewById(R.id.confInicioButt) as ImageButton
        buttonInicio.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        val buttonRecomend: ImageButton = findViewById(R.id.confRecomButt) as ImageButton
        buttonRecomend.setOnClickListener{
            val intent = Intent(this, MenuPrincipal::class.java)
            intent.putExtra("correo", correoGlobal);
            startActivity(intent)
        }
        var buttConf: ImageButton = findViewById(R.id.imageButton17) as ImageButton
        buttConf.setOnClickListener {
            val intent1 = Intent(this, MenuConfiguracion::class.java)
            intent1.putExtra("correo", correoGlobal);
            intent1.putExtra("IDUsuario", idUsuarioGlobal);
            startActivity(intent1)
        }
    }

    private fun getData(IDUser: String) {
        val queue = Volley.newRequestQueue(this);
        var listview = findViewById<ListView>(R.id.listViewHistorial)
        var list = mutableListOf<Model>()
        //val url = "http://192.168.1.45/kontakta/v1/getServ.php"
        //val url = "http://192.168.1.109/kontakta/v1/getServ.php"
        val url = "https://kontatkadb.000webhostapp.com/kontakta/v1/getHistorialUser.php"

        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    //Aqui es donde se jalan los datos desde la base en un jsonArray, puedes ver en el php como los traigo
                    val jsonArray= JSONArray(response)
                    //Aqui le digo que tome el raw 0 y que lo haga un jsonObject para poder usar los datos
                    var listID= mutableListOf<String>()
                    for(i in 0 until jsonArray.length()){
                        val jsonObject = JSONObject(jsonArray.getString(i))
                        if(!listID.contains(jsonObject.get("IDServicio_FK").toString())){
                        list.add(Model(jsonObject.get("nombrePrestador").toString(),jsonObject.get("IDServicio_FK").toString(),jsonObject.get("imagenPrestador").toString()))}
                        listID.add(jsonObject.get("IDServicio_FK").toString())
                    }
                    listview.adapter = MyAdapter(this,R.layout.row,list)
                    listview.setOnItemClickListener { parent: AdapterView<*>, view: View, position:Int, id:Long ->
                        println("posicion en la lista: $position")
                        println("IDServicio: "+list[position].correo)
                        val intent1 = Intent(this, perfilServ::class.java)
                        intent1.putExtra("IDServicio", list[position].correo);
                        intent1.putExtra("IDUsuario", IDUser);
                        startActivity(intent1)
                        //deleteHistorial(list[position].IDUsuario,IDUser)
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

    private fun deleteHistorial(IDHist: String, IDUser: String) {
        val queue = Volley.newRequestQueue(this);
        var listview = findViewById<ListView>(R.id.listViewHistorial)
        var list = mutableListOf<Model>()
        //val url = "http://192.168.1.45/kontakta/v1/getServ.php"
        //val url = "http://192.168.1.109/kontakta/v1/getServ.php"
        val url = "https://kontatkadb.000webhostapp.com/kontakta/v1/borrarHistorial.php"

        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {

                        val intent1 = Intent(this, vistaHistorial::class.java)
                        intent1.putExtra("IDUsuario", IDUser);
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
                params.put("IDHistCont", IDHist)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }
}