package com.example.capstoneproject.firestore

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.IOException

class GlideLoader(val context: Context) {
    fun loadImage(imageURI: Uri, imageView: ImageView){
        try {
            Glide
                .with(context)
                .load(imageURI)
                .centerCrop()
                .into(imageView)
        }catch (e: IOException){
            e.printStackTrace()
        }
    }
}