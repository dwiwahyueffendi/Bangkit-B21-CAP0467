package com.example.capstoneproject.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.capstoneproject.view.HomeFragment
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnLogin: TextView = findViewById(R.id.btn_sign_up_login)
        btnLogin.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()

        binding.btnSignUpRegister.setOnClickListener {
            val fullName = et_sign_up_full_name.text.toString().trim()
            val email = et_sign_up_email.text.toString().trim()
            val password = et_sign_up_password.text.toString().trim()

            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)

            if (fullName.isEmpty()) {
                binding.apply {
                    etSignUpFullName.error = "Full Name is required"
                    etSignUpFullName.requestFocus()
                    return@setOnClickListener
                }
            }

            if (email.isEmpty()) {
                binding.apply {
                    etSignUpEmail.error = "Email is required"
                    etSignUpEmail.requestFocus()
                    return@setOnClickListener
                }
            }

            if (password.isEmpty() || password.length < 6) {
                binding.apply {
                    etSignUpPassword.error = "Password must be at least 6 characters"
                    etSignUpPassword.requestFocus()
                    return@setOnClickListener
                }
            }

            registerUser(email, password)
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Registration is Successful !", Toast.LENGTH_SHORT)
                        .show()
                    Intent(this@SignUpActivity, HomeFragment::class.java).also { intent ->
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
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