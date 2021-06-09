package com.example.capstoneproject.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivitySignInBinding
import com.example.capstoneproject.firestore.FirestoreClass
import com.example.capstoneproject.model.ModelUser
import com.example.capstoneproject.ui.DashboardActivity
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnSignInLogin.setOnClickListener {
            val email = binding.etSigninEmail.text.toString().trim()
            val password = binding.etSigninPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.apply {
                    etSigninEmail.error = "Email wajib di isi"
                    etSigninEmail.requestFocus()
                    return@setOnClickListener
                }
            }

            if (password.isEmpty() || password.length < 6) {
                binding.apply {
                    etSigninPassword.error = "Password harus terdiri minimal 6 karakter"
                    etSigninPassword.requestFocus()
                    return@setOnClickListener
                }
            }

            loginUser(email,password)
        }

        val btnRegister: TextView = findViewById(R.id.btn_signin_Register)
        btnRegister.setOnClickListener(this)
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    FirestoreClass().getUserDetail(this@SignInActivity)

                    Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_signin_Register -> {
                val moveIntent = Intent(this@SignInActivity, SignUpActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(moveIntent)
            }
        }
    }

    fun userLoggedInSuccess(user: ModelUser){
        Log.i("ID: ", user.id)
        Log.i("Fullname: ", user.fullName)
        Log.i("Email: ", user.email)

        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}