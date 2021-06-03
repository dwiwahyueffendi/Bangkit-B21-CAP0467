package com.example.capstoneproject.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityReportBinding
import com.example.capstoneproject.firestore.FirestoreClass
import com.example.capstoneproject.firestore.GlideLoader
import com.example.capstoneproject.utils.Constant
import java.io.IOException

class ReportActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityReportBinding
    private var mSelectedImage: Uri? = null
    private var mImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnUploadImage.setOnClickListener(this@ReportActivity)
            btnUploadAduan.setOnClickListener(this@ReportActivity)
        }
    }

    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                R.id.btn_upload_image ->{
                    if(ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ){
                        Constant.showImageChooser(this)
                        Toast.makeText(this,"You already have the storage permission.", Toast.LENGTH_SHORT).show()
                    } else{

                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constant.READ_PERMISSION
                        )
                    }
                }

                R.id.btn_upload_aduan ->{
                    if (mSelectedImage != null){
                        FirestoreClass().uploadImagetoCloudStorage(this, mSelectedImage)
                        Toast.makeText(this,"Image Uploaded.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        updateImage()
                    }
                }
            }
        }
    }

    private fun updateImage(){
        val userHashMap = HashMap<String, Any>()
        if (mImageURL.isNotEmpty()) {
            userHashMap[Constant.IMAGE] = mImageURL
        }
        FirestoreClass().updateImageURL(this, userHashMap)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constant.READ_PERMISSION){
            if (grantResults.isNotEmpty()&& grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Constant.showImageChooser(this)

            }else{
                Toast.makeText(this,"read storage permission is denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == Constant.PICK_IMAGE){
                if (data != null){
                    try {
                        mSelectedImage = data.data!!

                        GlideLoader(this).loadImage(mSelectedImage!!, binding.ivLaporan)
                    }catch (e: IOException){
                        e.printStackTrace()
                        Toast.makeText(this,"Image selection failed.", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }

    fun imageUploadSuccess(imageURL: String){

        Toast.makeText(this@ReportActivity,"Upload Image successfully. Image URL is $imageURL", Toast.LENGTH_SHORT).show()

        mImageURL = imageURL
        updateImage()
    }
}