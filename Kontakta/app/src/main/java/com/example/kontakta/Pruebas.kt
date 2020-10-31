package com.example.kontakta

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class Pruebas : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pruebas)

        val tvRes= findViewById<TextView>(R.id.tvRes)
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.6/v1/usuariosGET.php"
        val stringRequest = StringRequest(Request.Method.GET,url, { response ->
            val jsonArray=JSONArray(response)
            val jsonObject=jsonArray
            tvRes.text=jsonObject.toString()
        }, { error ->

        })
        queue.add(stringRequest)
    }
}