package com.example.capstoneproject.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constant {
    const val REPORT: String = "report"
    const val MYREPORT_PREFERENCE: String = "myreportpreferences"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"
    const val READ_PERMISSION = 2
    const val PICK_IMAGE = 1
    const val DESC: String = "keterangan"
    const val LOCATION: String = "lokasi"
    const val IMAGE: String = "gambar"
    const val TIME: String = "waktuKejadian"
    const val STATUS: String = "status"

    fun showImageChooser(activity: Activity){
        val galeryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        activity.startActivityForResult(galeryIntent, PICK_IMAGE)
    }

    fun getFileEXtension(activity: Activity, uri: Uri?): String?{
        return MimeTypeMap
            .getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}