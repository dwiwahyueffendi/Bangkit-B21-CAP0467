package com.example.capstoneproject.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.capstoneproject.R
import com.example.capstoneproject.data.ReportResponse
import com.example.capstoneproject.databinding.ActivityReportBinding
import com.example.capstoneproject.firestore.FirestoreClass
import com.example.capstoneproject.firestore.GlideLoader
import com.example.capstoneproject.network.RetrofitClient
import com.example.capstoneproject.utils.Constant
import com.example.capstoneproject.utils.DateUtils
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

class ReportActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityReportBinding
    private var mSelectedImage: Uri? = null
    private var mImageURL: String = ""
    private var baseString64: String = ""
    private var jsonString: String = ""

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
                        //FirestoreClass().uploadImagetoCloudStorage(this, mSelectedImage)
                        //Toast.makeText(this,"Aduan di kirim", Toast.LENGTH_SHORT).show()

                        //val intent = Intent(this@ReportActivity, DashboardActivity::class.java)
                        //startActivity(intent)

                        /*var bitmap = convertImageToBitmap()
                        binding.ivLaporan.setImageBitmap(bitmap)
                        baseString64 = convertImageToBase64(bitmap)
                        val utfString = String(baseString64, charset("UTF-8"))*/

                        val bitmap = convertImageToBitmap()
                        binding.ivLaporan.setImageBitmap(bitmap)
                        val byteArrayOutputStream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                        val imageBytes = byteArrayOutputStream.toByteArray()
                        val imgString = Base64.encode(imageBytes, Base64.DEFAULT)
                        val utfString = String(imgString, charset("UTF-8"))
                        jsonString = "{\"data\": \"$utfString\"}"

                        /*val byteArrayOutputStream = ByteArrayOutputStream()
                        originalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                        val imageBytes = byteArrayOutputStream.toByteArray()
                        val imgString = Base64.encode(imageBytes, Base64.DEFAULT)
                        val utfString = String(imgString, charset("UTF-8"))
                        val jsonString = "{\"data\": \"$utfString\"}"*/

                        Log.d("jSonString", "jsonString = $jsonString")

                        createPost()
                    }
                    else {
                        //updateImage()
                    }
                }
            }
        }
    }

    private fun createPost() {
        //delay(6000)

        RetrofitClient.getReport().createPost(
            data = jsonString
        ).enqueue(object : Callback<ReportResponse> {
            override fun onResponse(
                call: Call<ReportResponse>,
                response: Response<ReportResponse>
            ) {
                val responseText =  "Responde Code : ${response.body()?.data}"
                        //"Base64 : ${response.body()?.data}"

                binding.tvStatusCode.text = responseText
            }

            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                binding.tvStatusCode.text = t.message
            }

        })
    }

    private fun convertImageToBase64(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val image = stream.toByteArray()
        val imgString = Base64.encodeToString(image, Base64.DEFAULT)
        return imgString
    }

    private fun convertImageToBitmap(): Bitmap {
        val drawable = binding.ivLaporan.drawable as BitmapDrawable
        return drawable.bitmap
    }


    private fun updateImage(){
        val mDesc = binding.etKeterangan.text.toString().trim { it <= ' '}
        val mLocation = binding.etLokasi.text.toString().trim { it <= ' '}

        val userHashMap = HashMap<String, Any>()
        if (mImageURL.isNotEmpty()) {
            userHashMap[Constant.DESC] = mDesc
            userHashMap[Constant.LOCATION] = mLocation
            userHashMap[Constant.IMAGE] = mImageURL
            userHashMap[Constant.TIME] = DateUtils.getNowDate()
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
                        Toast.makeText(this,"Gambar yang dipilih gagal.", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }

    fun imageUploadSuccess(imageURL: String){

        Toast.makeText(this@ReportActivity,"Upload gambar berhasil. Gambar URL : $imageURL", Toast.LENGTH_SHORT).show()

        mImageURL = imageURL
        updateImage()
    }
}