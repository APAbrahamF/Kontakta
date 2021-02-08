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

class MenuPrincipal: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_principal)

        var IDUser : String = intent.getStringExtra("IDUsuario").toString()
        var IDServ : String = intent.getStringExtra("IDServicio").toString()
        var correo : String = intent.getStringExtra("correo").toString()
        setFK(IDUser,IDServ)
        println("INTENT==============Correo: $correo")
        println("INTENT====================================================================IDServicio: $IDServ")
        println("INTENT====================================================================IDUsuario: $IDUser")

        var buttConf:ImageButton = findViewById(R.id.confButton) as ImageButton
        buttConf.setOnClickListener{
            val intent1 = Intent(this, MenuConfiguracion::class.java)
            intent1.putExtra("correo", correo);
            startActivity(intent1)
        }
        /*var cv=findViewById(R.id.card_view) as CardView
        cv.setOnClickListener{
            val intent1 = Intent(this, Pruebas2::class.java)
            intent1.putExtra("correo", correo);
            startActivity(intent1)
        }*/
        val arrayList = ArrayList<ModelCard>()
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

        val myAdapterCard = MyAdapterCard(arrayList,this)
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

}