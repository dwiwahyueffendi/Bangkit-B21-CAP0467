package com.example.capstoneproject.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import com.example.capstoneproject.auth.SignInActivity
import com.example.capstoneproject.auth.SignUpActivity
import com.example.capstoneproject.model.ModelUser
import com.example.capstoneproject.ui.ReportActivity
import com.example.capstoneproject.utils.Constant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.HashMap

class FirestoreClass {

    private val mFirestore = FirebaseFirestore.getInstance()

    fun getCurrentUserID(): String{
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserId = ""
        if(currentUser != null){
            currentUserId = currentUser.uid
        }
        return currentUserId
    }

    fun getUserDetail(activity: Activity){
        mFirestore.collection(Constant.REPORT)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.i(activity.toString().javaClass.simpleName, document.toString())

                val user = document.toObject(ModelUser::class.java)!!

                val sharedPreferences = activity.getSharedPreferences(
                    Constant.MYREPORT_PREFERENCE,
                    Context.MODE_PRIVATE
                )

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    Constant.LOGGED_IN_USERNAME,
                    user.fullName
                )
                editor.apply()

                when(activity){
                    is SignInActivity -> {
                        activity.userLoggedInSuccess(user)
                    }
                    is SignUpActivity -> {
                        activity.userRegisterSucsess(user)
                    }
                }
            }
            .addOnFailureListener {
                Log.e(activity.javaClass.simpleName,
                    "Gagal Mendaftar!!!")
            }
    }

    fun uploadImagetoCloudStorage(activity: Activity, imageFileURI: Uri?){
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            Constant.IMAGE + System.currentTimeMillis()+"."
                    +Constant.getFileEXtension(
                activity,
                imageFileURI
            )
        )
        sRef.putFile(imageFileURI!!).addOnSuccessListener {
            Log.e(
                "Firebase Image URL",
                it.metadata!!.reference!!.downloadUrl.toString()
            )
            it.metadata!!.reference!!.downloadUrl
                .addOnSuccessListener {
                    Log.e("Downloadable image URL", it.toString())
                    when (activity){
                        is ReportActivity ->{
                            activity.imageUploadSuccess(it.toString())
                        }
                    }
                }
        }
            .addOnFailureListener {
                when(activity){
                    is ReportActivity->{
                        Log.e(
                            activity.javaClass.simpleName,
                            it.message,
                            it
                        )
                    }
                }
                Log.e(
                    activity.javaClass.simpleName,
                    it.message,
                    it
                )
            }
    }

    fun updateData(activity: ReportActivity, userHashMap: HashMap<String, Any>) {
        mFirestore.collection(Constant.REPORT)
            .document(getCurrentUserID())
            .update(userHashMap)
            .addOnSuccessListener {
                when(activity){
                    is ReportActivity ->{

                    }
                }
            }
            .addOnFailureListener {
                when(activity){
                    is ReportActivity->{

                    }
                }
            }
    }
}