package com.example.capstoneproject.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivitySignUpBinding
import com.example.capstoneproject.utils.Constant
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnLogin: TextView = findViewById(R.id.btn_sign_up_login)
        btnLogin.setOnClickListener(this)

        binding.btnSignUpRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun validatingRegister(): Boolean{
        return when{
            TextUtils.isEmpty(et_sign_up_full_name.text.toString().trim{ it <= ' '}) -> {
                et_sign_up_full_name.error = "Full Name is required"
                et_sign_up_full_name.requestFocus()
                false
            }
            TextUtils.isEmpty(et_sign_up_email.text.toString().trim{ it <= ' '}) -> {
                et_sign_up_email.error = "Email is required"
                et_sign_up_email.requestFocus()
                false
            }
            TextUtils.isEmpty(et_sign_up_password.text.toString().trim{ it <= ' '}) -> {
                et_sign_up_password.error = "Password must be at least 6 characters"
                et_sign_up_password.requestFocus()
                false
            }
            else -> true
        }
    }

    private fun registerUser() {
        if(validatingRegister()){
            val fullName = et_sign_up_full_name.text.toString().trim { it <= ' '}
            val email = et_sign_up_email.text.toString().trim { it <= ' '}
            val password = et_sign_up_password.text.toString().trim { it <= ' '}

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        /*val user = User(
                            id = firebaseUser.uid,
                            fullName = et_sign_up_full_name.text.toString().trim { it <= ' '},
                            email = et_sign_up_email.text.toString().trim { it <= ' '}
                        )

                        FirestoreClass().registerUser(this, user)*/

                        val userRef = FirebaseFirestore.getInstance().collection(Constant.USERS)
                        val user = HashMap<String, Any>()
                        user["id"] = firebaseUser.uid
                        user["email"] = email
                        user["fullName"] = fullName
                        user["tipeAduan"] = ""
                        user["tanggalKejadian"] = ""
                        user["lokasi"] = ""
                        user["keterangan"] = ""
                        user["gambar"] = ""
                        user["status"] = "Belum Terkonfirmasi"

                        userRef.add(user).addOnSuccessListener {
                            Toast.makeText(this, "Registration is Successful !", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                        }

                        Toast.makeText(this, "Registration is Successful !", Toast.LENGTH_SHORT).show()
                        Intent(this@SignUpActivity, SignInActivity::class.java).also { intent ->
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_sign_up_login -> {
                val intent = Intent(this, SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}