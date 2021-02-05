package com.example.kontakta

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.menu_principal.*

class MenuPrincipal: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_principal)
        var correo : String = intent.getStringExtra("correo").toString()
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

}