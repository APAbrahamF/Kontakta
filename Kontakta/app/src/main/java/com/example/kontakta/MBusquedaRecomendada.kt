package com.example.kontakta

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.ThreadLocalRandom

class MBusquedaRecomendada: AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.busqueda_recomendada)
        val boton: Button = findViewById(R.id.buttonAccionar) as Button;
        boton.setOnClickListener {
            //El correo con el que se trabaja esta hardcodeado y se pone aqui
            getData("hu")
        }
        /*val botMenu: ImageButton = findViewById(R.id.recMenuInButt) as ImageButton
        botMenu.setOnClickListener{
            val intent = Intent(this,MenuPrincipal::class.java)
            startActivity(intent)
        }
        val botConf: ImageButton = findViewById(R.id.recConfButt) as ImageButton
        botConf.setOnClickListener{
            val intent = Intent(this, MenuConfiguracion::class.java)
            startActivity(intent)
        }*/
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getData(correo: String) {

        var listview = findViewById<ListView>(R.id.listView)
        var list = mutableListOf<Model2>()
        val queue = Volley.newRequestQueue(this)
        //Acuerdense de camnbiar el IP
        //val url = "http://192.168.1.109/kontakta/v1/historialGET.php"
        //val url = "http://192.168.1.45/kontakta/v1/historialGET.php"
        val url = "http://192.168.100.6/v1/historialGET.php"
        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            val jsonArray = JSONArray(response)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = JSONObject(jsonArray.getString(i))
                list.add(
                    Model2(
                        jsonObject.get("estadoUser").toString(),
                        jsonObject.get("sexoUser").toString(),
                        jsonObject.get("municipioUser").toString(),
                        jsonObject.getInt("edadUser"),
                        jsonObject.get("nombrePrestador").toString(),
                        jsonObject.get("imagenPrestador").toString(),
                        jsonObject.get("IDServicio_FK").toString()
                    )
                )
            }
            getUser(list, correo)
        }, { error ->

        })
        queue.add(stringRequest)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun kmedoid(list: MutableList<Model2>, instancia: Model3): MutableList<Model2> {
        //Aqui empiezo haciendo numeros random para crear una instancia al azar y la otra instancia son los datos del usuario
        var tamaño: Int = list.size;
        Toast.makeText(applicationContext, tamaño.toString(), Toast.LENGTH_LONG).show()
        var list1 = mutableListOf<Model2>()
        var list2 = mutableListOf<Model2>()
        val randomDouble1 = ThreadLocalRandom.current().nextInt(0, tamaño)
        val randomDouble2 = ThreadLocalRandom.current().nextInt(0, tamaño)
        val randomDouble3 = ThreadLocalRandom.current().nextInt(0, tamaño)
        val randomDouble4 = ThreadLocalRandom.current().nextInt(0, tamaño)
        var instancia2 = Model3(
            list[randomDouble2].estadoUser,
            list[randomDouble1].sexUser,
            list[randomDouble3].municipioUser,
            list[randomDouble4].edadUser
        );
        Toast.makeText(applicationContext, "Dentro del Kmedoid ", Toast.LENGTH_LONG).show()
        var contador: Int;
        var contador2: Int = 0;
        var distancia1: Double;
        var distancia2: Double;
        //Esto lo van a ver mas adelante y solo es para calcular la distancia euclidiana de la edad
        var doubleDist1: Double = distanciaEuclidiana(instancia,list)
        do
        {
            //Aqui paso los datos a listas temporales para poder hacer las comparaciones con las listas viejas
            var list1Temp = mutableListOf<Model2>()
            var list2Temp = mutableListOf<Model2>();
            list1Temp = igualar(list1, list1Temp)
            list2Temp = igualar(list2, list2Temp)
            list1.clear();
            list2.clear();
            for(contador in 0 until tamaño)
            {
                //no le puedo sumar lo de la distancia euclidiana por que aun no es tan variado asi que siempre se inclinara hacia un lado
                var doubleDist2: Double = distanciaEuclidiana(instancia2,list)
                distancia1 = distanciaTotal(instancia, list[contador]).toDouble()// + doubleDist1
                distancia2 = distanciaTotal(instancia2, list[contador]).toDouble()// + doubleDist2
                if(distancia1 < distancia2)
                    list1.add(list[contador])
                else
                    list2.add(list[contador])
            }
            //deje estas cosas para que se vea qui si esta funcionando, esto muestra los tamaños de las listas y las vueltas que van
            val tamano1 = list1.size
            val tamano2 = list2.size
            Toast.makeText(applicationContext, "tamano1 " + tamano1, Toast.LENGTH_LONG).show()
            Toast.makeText(applicationContext, "tamano2 " + tamano2, Toast.LENGTH_LONG).show()
            //Esto lo tienen que cambiar para usar kmean o kmedoid
            instancia2 = medoids(instancia2, list2);
            //instancia2 = mean(instancia2, list2);
            Toast.makeText(applicationContext, "Vuelta " + contador2, Toast.LENGTH_LONG).show()
            contador2++;
        }
        while(!list1Temp.equals(list1) && !list2Temp.equals(list2))
        //Aqui el silhouette, no esta regularizado por lo que te marca el valor total
        val silouette = silouette(list1, instancia, list2, instancia2)
        Toast.makeText(applicationContext, "Silouette " + silouette, Toast.LENGTH_LONG).show()
        //revisa el model2, tiene todo lo necesario para poder mostrar la lista de prestadores desde esta lista
        println("=======================TERMINADO")
        var listview = findViewById<ListView>(R.id.listViewBR)
        var list = mutableListOf<Model>()
        var listID= mutableListOf<String>()
        for(cont in 0 until list1.size)
        {
            if(!listID.contains(list1[cont].IDServicio_FK)){
            list.add(Model(list1[cont].nombrePrestador,list1[cont].IDServicio_FK,list1[cont].imagenPrestador))}
            //println(list1[cont].estadoUser + "\n")
            listID.add(list1[cont].IDServicio_FK)
        }
        listview.adapter = MyAdapterTest(this,R.layout.row,list)
        listview.setOnItemClickListener { parent: AdapterView<*>, view: View, position:Int, id:Long ->
            val intent1 = Intent(this, perfilServ::class.java)
            intent1.putExtra("IDServicio", list[position].correo);
            intent1.putExtra("IDUsuario", "115");
            startActivity(intent1)
        }
        return list1
    }

    private fun mean(instancia2: Model3, lista2: MutableList<Model2>): Model3 {
        //Aqui se calcula la media, normalize el sexo y omiti el municipio, tambien se normalizo el estado de 0 a 31
        var tamano: Int = lista2.size
        var edad: Int = 0;
        var sexo1: Double = 0.0;
        var estado: Int = 0
        var instanciaFinal: Model3 = instancia2
        for(cont in 0 until tamano)
        {
            edad += lista2[cont].edadUser
        }
        edad /= tamano
        for(cont in 0 until tamano)
        {
            if(lista2[cont].sexUser == "H")
                sexo1++
        }
        sexo1 /= tamano
        if(sexo1 > 0.59)
            sexo1 = 1.0
        else
            sexo1 = 0.0
        val arrayList = estados()
        val tamano2: Int = arrayList.size
        for(cont in 0 until tamano)
        {
            for(cont2 in 0 until tamano2)
            {
                if(lista2[cont].estadoUser == arrayList[cont2])
                    estado += cont2
            }
        }
        estado /= tamano
        instanciaFinal.edadUser = edad
        instanciaFinal.estadoUser = arrayList[estado]
        if(sexo1 == 0.0)
            instanciaFinal.sexUser = "M"
        else
            instanciaFinal.sexUser = "H"
        return instanciaFinal
    }

    private fun estados(): ArrayList<String> {
        //Esto solo mete los estados en un arraylist para poder hacer comparaciones
        val arrayList = ArrayList<String>()
        arrayList.add("Aguas calientes");
        arrayList.add("Baja california norte");
        arrayList.add("Baja california sur");
        arrayList.add("Campeche");
        arrayList.add("Chiapas");
        arrayList.add("Chihuhua");
        arrayList.add("Coahuila");
        arrayList.add("Colima");
        arrayList.add("CDMX");
        arrayList.add("Durango");
        arrayList.add("Estado de Mexico");
        arrayList.add("Guanajuato");
        arrayList.add("Guerrero");
        arrayList.add("Hidalgo");
        arrayList.add("Morelos");
        arrayList.add("Nayarit");
        arrayList.add("Nuevo leon");
        arrayList.add("Oaxaca");
        arrayList.add("Puebla");
        arrayList.add("Queretaro");
        arrayList.add("Quintana roo");
        arrayList.add("San Luis Potosi");
        arrayList.add("Sinaloa");
        arrayList.add("Sonora");
        arrayList.add("Tabasco");
        arrayList.add("Tamaulipas");
        arrayList.add("Tlaxcala");
        arrayList.add("Veracruz");
        arrayList.add("Yucatan");
        arrayList.add("Zacatecas");
        return arrayList
    }

    private fun medoids(instancia2: Model3, lista2: MutableList<Model2>): Model3 {
        //Aqui se calculan los medoids que solo es buscar la instancia con menos distancia hacia todos los demas valores
        var instanciaFinal = instancia2;
        var cont: Int;
        var cont1: Int;
        var dist1: Int = 0;
        var dist2: Int;
        val tamaño: Int = lista2.size
        for(cont in 0 until tamaño)
        {
            var distTemp: Int = 0;
            distTemp = distanciaTotal(instancia2, lista2[cont])
            dist1 += distTemp
        }
        for(cont1 in 0 until tamaño)
        {
            dist2 = 0
            var instanciaT: Model3 = Model3(
                lista2[cont1].estadoUser,
                lista2[cont1].sexUser,
                lista2[cont1].municipioUser,
                lista2[cont1].edadUser
            )
            for(cont in 0 until tamaño)
            {
                var distTemp: Int = 0;
                distTemp = distanciaTotal(instanciaT, lista2[cont])
                dist2 += distTemp
            }
            if(dist2 < dist1)
            {
                dist1 = dist2
                instanciaFinal = instanciaT
            }
        }
        return instanciaFinal
    }

    private fun igualar(list1: MutableList<Model2>, list2: MutableList<Model2>): MutableList<Model2> {
        //Esto pasa los valores de una lista a otra, lo tuve que hacer asi por que al parecer esta cosa
        //al usar un = solo lo referenciaba
        var cont: Int;
        var tamaño: Int = list1.size
        for(cont in 0 until tamaño)
        {
            list2.add(list1[cont])
        }
        return list2
    }

    private fun distanciaEuclidiana(instancia1: Model3, list1: MutableList<Model2>): Double
    {
        //Aqui se calcula la distancia euclidiana, una copia de una funcion del P8
        var sumSquaredDiffs = 0.0
        val tamaño: Int = list1.size;

        for (j in 0 until tamaño)
        {
            var resta: Double = list1[j].edadUser.toDouble() - instancia1.edadUser.toDouble()
            sumSquaredDiffs += Math.pow(resta,2.0)
        }
        return Math.sqrt(sumSquaredDiffs)
    }

    private fun distanciaString(cadena1: String, cadena2: String): Int {
        //Aqui se calcula la distancia entre cadenas
        if(cadena1 == cadena2)
            return 0;
        else
            return 1;
    }

    private fun distanciaTotal(instancia1: Model3, instancia2: Model2): Int
    {
        //Esta funcion saca la distancia de los valores y regresa el total
        var dist: Int = 0;
        dist += distanciaString(instancia1.estadoUser, instancia2.estadoUser);
        dist += distanciaString(instancia1.municipioUser, instancia2.municipioUser);
        dist += distanciaString(instancia1.sexUser, instancia2.sexUser);
        //Toast.makeText(applicationContext, "distancia regresada: " + dist.toString(), Toast.LENGTH_LONG).show()
        return dist;
    }

    private fun silouette(list1: MutableList<Model2>, instancia1: Model3, list2: MutableList<Model2>, instancia2: Model3): Double
    {
        //Aqui se calcula el valor de silhouette donde se saca la distancia del centro de la lista1
        //Que es la que nos interesa a la lista1 y luego a la lista 2 para ver la cohecion
        var inst1 = medoids(instancia1, list1)
        var inst2 = medoids(instancia2, list2)
        //var inst1 = mean(instancia1, list1)
        //var inst2 = mean(instancia2, list2)
        val tamaño1 = list1.size
        val tamaño2 = list2.size
        var distancia1 : Double = 0.0
        var distancia2: Double = 0.0
        var valSilouette: Double
        for(cont in 0 until tamaño1)
        {
            if(!inst1.equals(list1[cont]))
            {
                var doubleDist: Double = distanciaEuclidiana(inst1, list1)
                distancia1 += (distanciaTotal(inst1, list1[cont]) + doubleDist) / (tamaño1 - 1)
            }
        }
        for(cont in 0 until tamaño2)
        {
            var doubleDist: Double = distanciaEuclidiana(inst1, list2)
            distancia2 += distanciaTotal(inst2, list2[cont]) + doubleDist / tamaño2
        }
        if(distancia2 > distancia1)
            valSilouette = (distancia2 - distancia1)/distancia2
        else
            valSilouette = (distancia2 - distancia1)/distancia1
        return valSilouette
    }

    private fun getUser(list: MutableList<Model2>, correo: String) {
        //Aqui jalo los editText para poder poner ahi los datos que voy a jalar desde la base
        val queue = Volley.newRequestQueue(this);
        var instanceT = Model3("", "", "", 0);
        //Aqui va la url de tu server, usa tu ip si vas a trabajar en tu celular
        //IP abraham
        //val url = "http://192.168.1.109/kontakta/v1/getUser.php"
        //IP Axel
        //val url = "http://192.168.1.45/kontakta/v1/getUser.php"
        //IP p8
        val url = "http://192.168.100.6/v1/getUser.php"
        //creating volley string request
        val stringRequest = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    //Aqui es donde se jalan los datos desde la base en un jsonArray, puedes ver en el php como los traigo
                    val jsonArray = JSONArray(response)
                    //Aqui le digo que tome el raw 0 y que lo haga un jsonObject para poder usar los datos
                    val obj = JSONObject(jsonArray.getString(0))
                    //A partir de aqui solo pongo los datos que jale en los espacios del edit text
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG)
                        .show()
                    obj.getString("nombre")
                    instanceT = Model3(
                        obj.getString("estado"),
                        obj.getString("sexo"),
                        obj.getString("municipio"),
                        obj.getInt("edad")
                    );
                    Toast.makeText(applicationContext, instanceT.municipioUser, Toast.LENGTH_LONG)
                        .show()
                    Toast.makeText(applicationContext, "Antes del kmedoid", Toast.LENGTH_LONG)
                        .show()
                    //Aqui llamo a la funcion Kmedoid o Kmeans la cosa es que esta medio enredoso alv
                    kmedoid(list, instanceT);
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
                //Esta funcion es la que pone los parametros en el php, aqui le pasas lo que va a ocupar el php
                params.put("correo", correo)
                return params
            }
        }
        //adding request to queue
        queue.add(stringRequest);
    }
}