package com.example.kontakta
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.registro_usuario.*
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
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class RegistroUsuario:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_usuario)
        val conexion = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val status = conexion.activeNetworkInfo

        val btnRegister: Button = findViewById(R.id.buttonRegistro) as Button;
        img_pick_btn.setOnClickListener {
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
        val ban: CheckBox = findViewById<CheckBox>(R.id.checkBox)
        ban.post {
            ban!!.setChecked(true)
            ban!!.jumpDrawablesToCurrentState() // This is most important
        }

        btnRegister.setOnClickListener() {
            var check = false;
            if (status != null && status.isConnected) {
                    addUser(ban)
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
            image_view.setImageURI(data?.data)
            /*val bm = (image_view.getDrawable() as BitmapDrawable).getBitmap()
            val stream = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.PNG, 90, stream)
            val byteArrayImage = stream.toByteArray()
            val encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT)
            println("Base64")
            println(encodedImage)*/
        }
    }

    private fun addUser(ban: CheckBox) {
        //getting the record values
        val queue = Volley.newRequestQueue(this);
        val nombre = textNombre?.text.toString()
        val edad = textEdad?.text.toString()
        val sexo = textSexo?.text.toString()
        val direccion = textDireccion?.text.toString()
        val municipio = textMunicipio?.text.toString()
        val estado = textEstado?.text.toString()
        val correo = textCorreo1?.text.toString()
        val pass2 = textPass2?.text.toString()
        val password = textPass1?.text.toString()

        val bm = (image_view.getDrawable() as BitmapDrawable).getBitmap()
        val stream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val byteArrayImage = stream.toByteArray()
        val encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT)
        //println("Base64")
        //println(encodedImage)

        val imagen : String = encodedImage;

        val url = "http://192.168.1.109/kontakta/v1/index.php"
        //val url = "http://192.168.100.6/v1/index.php"


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
                        Toast.makeText(applicationContext, ban.isChecked.toString(), Toast.LENGTH_LONG).show()
                        if(ban.isChecked) {
                            val intent1 = Intent(this, RegistroPrestador::class.java)
                            intent1.putExtra("correo", correo);
                            startActivity(intent1)
                        }
                        else
                        {
                            val intent2 = Intent(this, MenuPrincipal::class.java)
                            startActivity(intent2)
                        }
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
                params.put("edad", edad)
                params.put("sexo", sexo)
                params.put("direccion", direccion)
                params.put("municipio", municipio)
                params.put("estado", estado)
                params.put("correo", correo)
                params.put("password", password)
                params.put("imagen", imagen)
                return params
            }
        }
        if(password != pass2) {
            Toast.makeText(applicationContext, "Las contraseñas no son iguales", Toast.LENGTH_LONG).show()
        }
        else {
            //adding request to queue
            queue.add(stringRequest);
        }
    }

}
