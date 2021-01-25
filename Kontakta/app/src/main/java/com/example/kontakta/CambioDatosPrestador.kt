package com.example.kontakta

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.cambio_prestador.*
import kotlinx.android.synthetic.main.cambio_usuario.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream


var idServicio: String = ""

class CambioDatosPrestador : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cambio_prestador)
        var correo : String = intent.getStringExtra("correo").toString()
        getIDServicio(correo)
        getData(idServicio)
        val BotonGuardar: FloatingActionButton = findViewById(R.id.FABGuardarP) as FloatingActionButton;
        imageBtnPhotoPro2.setOnClickListener {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, CambioDatosUsuario.PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }
        BotonGuardar.setOnClickListener {
            guardar();
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        val IMAGE_PICK_CODE = 1000;
        //Permission code
        val PERMISSION_CODE = 1001;
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            imgViewPhotoPro2.setImageURI(data?.data)
        }
    }

    private fun getIDServicio(correo: String) {
        val queue = Volley.newRequestQueue(this);

        //val url = "http://192.168.1.45/kontakta/v1/getUser.php"
        //val url = "http://192.168.1.109/kontakta/v1/getUser.php"
        //val url = "http://192.168.100.6/v1/getUser.php"

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
                    idServicio = obj.getString("IDServicio_FK")
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
    private fun getData(IDServ: String) {
        val queue = Volley.newRequestQueue(this);
        var nombre: EditText = findViewById(R.id.textNombre2) as EditText
        var descripcion: EditText = findViewById(R.id.textDescripcionP) as EditText
        var integrantes: EditText = findViewById(R.id.textIntegrantesP) as EditText
        var facebook: EditText = findViewById(R.id.textFacebookP) as EditText
        var instagram: EditText = findViewById(R.id.textInstagramP) as EditText
        var youtube: EditText = findViewById(R.id.textYoutubeP) as EditText
        var twitter: EditText = findViewById(R.id.textTwitter) as EditText
        var genero: EditText = findViewById(R.id.editTextGenero) as EditText
        var imageview: ImageView = findViewById(R.id.imgViewPhotoPro2) as ImageView
        var imgCadena = "";

        //val url = "http://192.168.1.45/kontakta/v1/getServ.php"
        //val url = "http://192.168.1.109/kontakta/v1/getServ.php"
        //val url = "http://192.168.100.6/v1/getServ.php"


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
                    nombre.setText(obj.getString("nombreServicio"))
                    descripcion.setText(obj.getString("descripcion"))
                    integrantes.setText(obj.getString("integrantes"))
                    genero.setText(obj.getString("genero"))
                    facebook.setText(obj.getString("facebook"))
                    instagram.setText(obj.getString("instagram"))
                    youtube.setText(obj.getString("youtube"))
                    twitter.setText(obj.getString("twitter"))
                    imgCadena = obj.getString("imagen")
                    var extension = imgCadena
                    if(imgCadena.contains(","))
                        extension = extension.substringAfter(delimiter = ",", missingDelimiterValue = "Extension Not found")
                    //val extension = mItem.img.substringAfter(delimiter = ",", missingDelimiterValue = "Extension Not found")
                    val imageBytes = Base64.decode(extension, Base64.DEFAULT)
                    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    imageview.setImageBitmap(decodedImage)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                //Esta funcion es la que pone los parametros en el php, aqui le pasas lo que va a ocupar el php
                params.put("IDServ", IDServ)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }
    private fun guardar() {
        //getting the record values
        val queue = Volley.newRequestQueue(this);
        val nombreServicio = textNombre2?.text.toString()
        val descripcion = textDescripcionP?.text.toString()
        val integrantes = textIntegrantesP?.text.toString()
        val genero = editTextGenero?.text.toString()
        val facebook = textFacebookP?.text.toString()
        val youtube = textYoutubeP?.text.toString()
        val instagram = textInstagramP?.text.toString()
        val twitter = textTwitter?.text.toString()
        var imageview: ImageView = findViewById(R.id.imgViewPhotoPro2) as ImageView
        val bm = (imageview.getDrawable() as BitmapDrawable).getBitmap()
        val stream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val byteArrayImage = stream.toByteArray()
        val encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT)

        //val url = "http://192.168.1.45/kontakta/v1/actualizarServ.php"
        //val url = "http://192.168.1.109/kontakta/v1/actualizarServ.php"
        //val url = "http://192.168.100.6/v1/actualizarServ.php"



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
                params.put("IDServicio", idServicio)
                params.put("nombreServicio", nombreServicio)
                params.put("descripcion", descripcion)
                params.put("genero", genero)
                params.put("integrantes", integrantes)
                params.put("facebook", facebook)
                params.put("youtube", youtube)
                params.put("instagram", instagram)
                params.put("twitter", twitter)
                params.put("imagen", encodedImage)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }
}