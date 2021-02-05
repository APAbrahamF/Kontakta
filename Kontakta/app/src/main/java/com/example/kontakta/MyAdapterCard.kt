package com.example.kontakta

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_view.view.*

class MyAdapterCard(val arrayList: ArrayList<ModelCard>,val context: Context) :
    RecyclerView.Adapter<MyAdapterCard.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItems(modelCard: ModelCard){
            itemView.info_text.text=modelCard.genero
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])
        holder.itemView.setOnClickListener{
            val model =arrayList.get(position)
            var tempGenero:String = model.genero
            val intent=Intent(context, vistaGenero::class.java)
            intent.putExtra("genero",tempGenero)
            println("El genero seleccionado es: $tempGenero")
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}