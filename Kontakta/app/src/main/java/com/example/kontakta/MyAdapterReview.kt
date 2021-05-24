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

class MyAdapterReview (var mCtx: Context, var resources:Int, var items:List<ModelReview>):
    ArrayAdapter<ModelReview>(mCtx,resources, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(resources,null)

        val titleTextView: RatingBar = view.findViewById(R.id.rating_row_review)
        val descriptionTextView: TextView = view.findViewById(R.id.textView_row_review)
        //val idTextView: TextView = view.findViewById(R.id.textView2_row_review)

        var mItem:ModelReview = items[position]
        titleTextView.rating = mItem.valoracion.toFloat()
        descriptionTextView.text = mItem.comentario
       // idTextView.text = mItem.IDReview


        return view
    }
}