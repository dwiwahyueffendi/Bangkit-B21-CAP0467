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
                    etSigninEmail.error = "Email is required"
                    etSigninEmail.requestFocus()
                    return@setOnClickListener
                }
            }

            if (password.isEmpty() || password.length < 6) {
                binding.apply {
                    etSigninPassword.error = "Password must be at least 6 characters"
                    etSigninPassword.requestFocus()
                    return@setOnClickListener
                }
            }

            loginUser(email,password)
        }

        val btnRegister: TextView = findViewById(R.id.btn_signin_Register)
        btnRegister.setOnClickListener(this)

        binding.btnSigninForgotPassword.setOnClickListener {
            val intent = Intent(this@SignInActivity, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    //FirestoreClass().getUserDetail(this)

                    Toast.makeText(this, "Login is Successful !", Toast.LENGTH_SHORT).show()
                    Intent(this, DashboardActivity::class.java).also { intent ->
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
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