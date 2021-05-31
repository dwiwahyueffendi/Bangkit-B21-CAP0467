package com.example.capstoneproject.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.capstoneproject.NavigasiActivity
import com.example.capstoneproject.view.HomeFragment
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnSignInLogin.setOnClickListener {
            val email = et_signin_email.text.toString().trim()
            val password = et_signin_password.text.toString().trim()

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
        btnRegister.setOnClickListener(this@SignInActivity)

        binding.btnSigninForgotPassword.setOnClickListener {
            val intent = Intent(this@SignInActivity, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        /*binding.btnSigninRegister.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }*/


    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this, "Login is Successful !", Toast.LENGTH_SHORT).show()
                    Intent(this, NavigasiActivity::class.java).also { intent ->
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }else{
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_signin_Register -> {
                val moveIntent = Intent(this, SignUpActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(moveIntent)
            }
        }
    }
}