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

class componerReview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.componer_review)
        var IDServ : String = intent.getStringExtra("review").toString()
        Toast.makeText(this, IDServ, Toast.LENGTH_LONG).show()
        val conexion = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val status = conexion.activeNetworkInfo

        val btnRegister: Button = findViewById(R.id.buttonPublicar) as Button;
        btnRegister.setOnClickListener() {

            if (status != null && status.isConnected) {
                addReview(IDServ)
            } else {
                Toast.makeText(this, "Revise su conexion a internet", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun addReview(IDServ: String) {
        //getting the record values
        val queue = Volley.newRequestQueue(this);
        val comentario = textReview?.text.toString()
        val valoracion = rating?.rating.toString()

        //val url = "http://192.168.1.45/kontakta/v1/index.php"
        //val url = "http://192.168.1.109/kontakta/v1/index.php"
        val url = "http://192.168.100.6/v1/insertReview.php"


        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                    var bandera = obj.getString("error");
                    if(bandera == "false")
                    {
                        val intent1 = Intent(this, Pruebas2::class.java)
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
                params.put("IDServicio_FK", IDServ)
                return params
            }
        }
            queue.add(stringRequest);
    }
}