package com.example.capstoneproject.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.capstoneproject.auth.SignInActivity
import com.example.capstoneproject.utils.Constant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User

class FirestoreClass {

    private val mFirestore = FirebaseFirestore.getInstance()

    /*fun registerUser(activity: SignUpActivity, userInfo: User){
        mFirestore.collection("users")
            .document(userInfo.id)
            .set(userInfo.id, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegristrationSuccess()
            }
            .addOnFailureListener{ e ->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while regristering user",
                    e
                )
            }
    }*/

    fun getCurrentUserID(): String{
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserId = ""
        if(currentUser != null){
            currentUserId = currentUser.uid
        }
        return currentUserId
    }

    fun getUserDetail(activity: Activity){
        mFirestore.collection(Constant.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.i(activity.toString().javaClass.simpleName, document.toString())

                val user = document.toObject(User::class.java)!!

                val sharedPref = activity.getSharedPreferences(
                    Constant.MYREPORT_PREFERENCE,
                    Context.MODE_PRIVATE
                )

                /*val editor: SharedPreferences.Editor = sharedPref.edit()
                editor.putString(
                    Constant.LOGGED_IN_USERNAME,
                    "${user.fullName}"
                )

                when(activity){
                    is SignInActivity -> activity.userLoggedInSuccess(user)
                }*/
            }
    }

}