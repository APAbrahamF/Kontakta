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
import kotlinx.android.synthetic.main.registro_usuario.textMunicipio
import kotlinx.android.synthetic.main.registro_usuario.textNombre
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import android.app.Activity
import android.content.pm.PackageManager
import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Build.*
import android.util.Base64
import android.widget.ImageView
import java.io.ByteArrayOutputStream


class CambioDatosUsuario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cambio_usuario)
        //Esta es la funcion que jala los datos actuales del usuario
        //ese correo lo tengo en la base, despues jalaremos el correo del usuario actual desde el menu
        //por el momento usa un correo que tengas tu registrado para jalar los registros
        var correo : String = intent.getStringExtra("correo").toString()
        getData(correo)
        //Aqui estoy jalando el boton desde la interfaz para luego crear el evento oncreate
        var BotonGuardar: FloatingActionButton = findViewById(R.id.FABGuardarU) as FloatingActionButton;
        imageBtnPhotoPro.setOnClickListener {
            //check runtime permission
            if (VERSION.SDK_INT >= VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
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
            //Esta es la funcion que jala datos bien chidori, igual le tienes que pasar el correo del usuario para saber donde guardar
            guardar(correo);
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
            imgViewPhotoPro.setImageURI(data?.data)
        }
    }
    private fun getData(correo: String) {
        //Aqui jalo los editText para poder poner ahi los datos que voy a jalar desde la base
        val queue = Volley.newRequestQueue(this);
        var nombre: EditText = findViewById(R.id.textNombre) as EditText
        var sexo: EditText = findViewById(R.id.textSexo3) as EditText
        var edad: EditText = findViewById(R.id.textEdad) as EditText
        var municipio: EditText = findViewById(R.id.textMunicipio) as EditText
        var direccion: EditText = findViewById(R.id.textDireccion) as EditText
        var estado: EditText = findViewById(R.id.textEstadoCambio) as EditText
        var imageview: ImageView = findViewById(R.id.imgViewPhotoPro) as ImageView
        var imgCadena = "";

        //Aqui va la url de tu server, usa tu ip si vas a trabajar en tu celular
        //IP abraham
        //val url = "http://192.168.1.109/kontakta/v1/getUser.php"
        //IP Axel
        val url = "http://192.168.1.45/kontakta/v1/getUser.php"
        //IP p8
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
                    nombre.setText(obj.getString("nombre"))
                    sexo.setText(obj.getString("sexo"))
                    edad.setText(obj.getString("edad"))
                    municipio.setText(obj.getString("municipio"))
                    direccion.setText(obj.getString("direccion"))
                    estado.setText(obj.getString("estado"))
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
        val estado = textEstadoCambio?.text.toString()
        var imageview: ImageView = findViewById(R.id.imgViewPhotoPro) as ImageView
        val bm = (imageview.getDrawable() as BitmapDrawable).getBitmap()
        val stream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val byteArrayImage = stream.toByteArray()
        val encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT)

        //IP abraham
        //val url = "http://192.168.1.109/kontakta/v1/actualizarUser.php"
        //IP Axel
        //val url = "http://192.168.1.45/kontakta/v1/actualizarUser.php"
        //IP p8
        val url = "http://192.168.100.6/v1/actualizarUser.php"

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
                params.put("imagen", encodedImage)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }
}