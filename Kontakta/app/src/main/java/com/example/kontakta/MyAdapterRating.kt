package com.example.kontakta

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView

class MyAdapterRating (var mCtx: Context, var resources:Int, var items:List<ModelRating>):
    ArrayAdapter<ModelRating>(mCtx,resources, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(resources,null)

        val imageView: ImageView = view.findViewById(R.id.image_row_rating)
        val ratingbar: RatingBar = view.findViewById(R.id.rating_row_rating)
        val nombreTextView: TextView = view.findViewById(R.id.textView_row_rating)
        //val idTextView: TextView = view.findViewById(R.id.textView2_row_review)

        var mItem:ModelRating = items[position]
        var extension=mItem.imagenServ
        if(mItem.imagenServ.contains(","))
            extension = extension.substringAfter(delimiter = ",", missingDelimiterValue = "Extension Not found")
        //val extension = mItem.img.substringAfter(delimiter = ",", missingDelimiterValue = "Extension Not found")
        val imageBytes = Base64.decode(extension, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        imageView.setImageBitmap(decodedImage)
        ratingbar.rating = mItem.promedio.toFloat()
        nombreTextView.text = mItem.nombreServ
        // idTextView.text = mItem.IDReview


        return view
    }
}