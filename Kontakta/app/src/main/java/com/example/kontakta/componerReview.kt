package com.example.kontakta

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.componer_review.*
import kotlinx.android.synthetic.main.registro_usuario.*
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt

class componerReview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.componer_review)
        var IDServ : String = intent.getStringExtra("IDServicio").toString()
        var IDUser : String = intent.getStringExtra("IDUsuario").toString()
        var cantidad : String = intent.getStringExtra("cantidad").toString()
        var sumatoria : String = intent.getStringExtra("sumatoria").toString()
        //Toast.makeText(this, IDServ, Toast.LENGTH_LONG).show()
        println("COMPONER========================================================IDUser: $IDUser")
        println("=================================================================CANTIDAD en componerReview = $cantidad")
        println("=================================================================SUMATORIA en componerReview = $sumatoria")
        val conexion = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val status = conexion.activeNetworkInfo

        val btnRegister: Button = findViewById(R.id.buttonPublicar) as Button;
        btnRegister.setOnClickListener() {

            if (status != null && status.isConnected) {
                addReview(IDUser,IDServ,cantidad,sumatoria)
            } else {
                Toast.makeText(this, "Revise su conexion a internet", Toast.LENGTH_LONG).show()
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

    private fun addReview(IDUser: String, IDServ: String, cantidad: String, sumatoria: String) {
        //getting the record values
        val queue = Volley.newRequestQueue(this);
        val comentario = textReview?.text.toString()
        val valoracion = rating?.rating.toString()

        var newCantidad=cantidad.toFloat()+1
        var newSumatoria=sumatoria.toFloat()+valoracion.toFloat()
        var newPromedio=newSumatoria/newCantidad
        println("=================================================================NEW-SUMATORIA en componerReview = $newSumatoria")
        println("=================================================================NEW-CANTIDAD en componerReview = $newCantidad")
        println("=================================================================NEW-PROMEDIO en componerReview = $newPromedio")
        //val url = "https://kontatkadb.000webhostapp.com/kontakta/v1/index.php"
        //val url = "http://192.168.1.109/kontakta/v1/index.php"
        val url = "https://kontatkadb.000webhostapp.com/kontakta/v1/insertReview.php"


        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    //Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                    var bandera = obj.getString("error");
                    if(bandera == "false")
                    {
                        val intent1 = Intent(this, ActualizarPromedio::class.java)
                        intent1.putExtra("IDServicio", IDServ);
                        intent1.putExtra("IDUsuario", IDUser);
                        intent1.putExtra("Promedio", newPromedio.toString());
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
                params.put("comentario", comentario)
                params.put("valoracion", valoracion)
                params.put("IDUsuario_FK", IDUser)
                params.put("IDServicio_FK", IDServ)
                return params
            }
        }
            queue.add(stringRequest);
    }

}