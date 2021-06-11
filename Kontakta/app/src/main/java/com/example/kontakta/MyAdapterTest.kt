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

        val imageView:ImageView = view.findViewById(R.id.image_row)
        val titleTextView:TextView = view.findViewById(R.id.textView1_row)
        val descriptionTextView:TextView = view.findViewById(R.id.textView2_row)

        var mItem:Model = items[position]
        //imageView.setImageDrawable(mCtx.resources.getDrawable(mItem.img))
        var extension=mItem.img
        if(mItem.img.contains(","))
            extension = extension.substringAfter(delimiter = ",", missingDelimiterValue = "Extension Not found")
        //val extension = mItem.img.substringAfter(delimiter = ",", missingDelimiterValue = "Extension Not found")
        val imageBytes = Base64.decode(extension, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        imageView.setImageBitmap(decodedImage)
        titleTextView.text = mItem.IDUsuario
        descriptionTextView.text = mItem.correo


        return view
    }
}