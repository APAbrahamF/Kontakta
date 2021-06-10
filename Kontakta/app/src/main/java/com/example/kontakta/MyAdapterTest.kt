package com.example.kontakta

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapterTest (var mCtx:Context, var resources:Int, var items:List<Model>):ArrayAdapter<Model>(mCtx,resources, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater:LayoutInflater= LayoutInflater.from(mCtx)
        val view:View = layoutInflater.inflate(resources,null)

        //val imageView:ImageView = view.findViewById(R.id.image_row)
        val titleTextView:TextView = view.findViewById(R.id.textView1_row)
        val descriptionTextView:TextView = view.findViewById(R.id.textView2_row)

        var mItem:Model = items[position]
        titleTextView.text = mItem.correo
        descriptionTextView.text = mItem.img


        return view
    }
}