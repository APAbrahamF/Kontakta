package com.example.kontakta

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.registro_prestador.*
import org.json.JSONException
import org.json.JSONObject
import android.app.Activity
import android.content.pm.PackageManager
import android.Manifest
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Build.*
import android.util.Base64
import kotlinx.android.synthetic.main.registro_usuario.*
import java.io.ByteArrayOutputStream


@Suppress("DEPRECATION")
class RegistroPrestador:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_prestador)
        val conexion = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val status = conexion.activeNetworkInfo

        val btnRegister: Button = findViewById(R.id.buttonRegistroServ) as Button;
        img_pick_btn2.setOnClickListener {
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


        var correo : String = intent.getStringExtra("correo").toString()
        btnRegister.setOnClickListener() {

            if (status != null && status.isConnected) {
                addPres(correo)
            } else {
                Toast.makeText(this, "Revise su conexion a internet", Toast.LENGTH_LONG).show()
            }

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
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
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
            image_view2.setImageURI(data?.data)
            val bm = (image_view2.getDrawable() as BitmapDrawable).getBitmap()
            val resizedBitmap = resizeBitmap(bm,300,300)
            image_view2.setImageBitmap(resizedBitmap)
        }
    }

    private fun addPres(correo: String) {
        //getting the record values
        val queue = Volley.newRequestQueue(this);
        val nombre = textNombreServ?.text.toString()
        //val imagen: String = "1234";
        val integrantes = textIntegrantes?.text.toString()
        val descripcion = textDescripcion?.text.toString()
        val genero = textGenero?.text.toString()
        val youtube = textYoutube?.text.toString()
        val instagram = textInstagram?.text.toString()
        val facebook = textFacebook?.text.toString()
        val twitter = textTwitter?.text.toString()

        val bm = (image_view2.getDrawable() as BitmapDrawable).getBitmap()
        val resizedBitmap = resizeBitmap(bm,300,300)
        image_view2.setImageBitmap(resizedBitmap)
        val stream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val byteArrayImage = stream.toByteArray()
        val encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT)
        //println("Base64")
        //println(encodedImage)

        val imagen : String = encodedImage;

        //val url = "http://192.168.1.45/kontakta/v1/insertM.php"
        //val url = "http://192.168.1.109/kontakta/v1/insertM.php"
        val url = "http://192.168.100.6/v1/insertM.php"


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
                        val intent1 = Intent(this, MenuPrincipal::class.java)
                        intent1.putExtra("correo", correo);
                        startActivity(intent1)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("nombre", nombre)
                params.put("integrantes", integrantes)
                params.put("descripcion", descripcion)
                params.put("genero", genero)
                params.put("youtube", youtube)
                params.put("instagram", instagram)
                params.put("facebook", facebook)
                params.put("twitter", twitter)
                params.put("correo", correo)
                params.put("imagen", imagen)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }

    private fun resizeBitmap(bitmap:Bitmap, width:Int, height:Int):Bitmap{
        return Bitmap.createScaledBitmap(
            bitmap,
            width,
            height,
            false
        )
    }
}