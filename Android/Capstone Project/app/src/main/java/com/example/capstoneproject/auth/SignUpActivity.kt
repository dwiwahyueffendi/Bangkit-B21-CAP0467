package com.example.capstoneproject.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivitySignUpBinding
import com.example.capstoneproject.firestore.FirestoreClass
import com.example.capstoneproject.model.ModelUser
import com.example.capstoneproject.ui.DashboardActivity
import com.example.capstoneproject.utils.Constant
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySignUpBinding

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
            TextUtils.isEmpty(binding.etSignUpFullName.text.toString().trim{ it <= ' '}) -> {
                binding.etSignUpFullName.error = "Nama lengkap harus di isi"
                binding.etSignUpFullName.requestFocus()
                false
            }
            TextUtils.isEmpty(binding.etSignUpFullName.text.toString().trim{ it <= ' '}) -> {
                binding.etSignUpFullName.error = "Email harus di isi"
                binding.etSignUpFullName.requestFocus()
                false
            }
            TextUtils.isEmpty(binding.etSignUpPassword.text.toString().trim{ it <= ' '}) -> {
                binding.etSignUpPassword.error = "Password minimal terdiri dari 6 karakter"
                binding.etSignUpPassword.requestFocus()
                false
            }
            else -> true
        }
    }

    private fun registerUser() {
        if(validatingRegister()){
            //val fullName = binding.etSignUpFullName.text.toString().trim { it <= ' '}
            val email = binding.etSignUpEmail.text.toString().trim { it <= ' '}
            val password = binding.etSignUpPassword.text.toString().trim { it <= ' '}

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {

                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        val user = ModelUser(
                            firebaseUser.uid,
                            binding.etSignUpFullName.text.toString().trim { it <= ' '},
                            binding.etSignUpEmail.text.toString().trim { it <= ' '}
                        )

                        FirebaseFirestore.getInstance().collection(Constant.REPORT)
                            .document(user.id)
                            .set(user, SetOptions.merge())
                            .addOnSuccessListener {
                                FirestoreClass().getUserDetail(this@SignUpActivity)
                            }
                            .addOnFailureListener {

                            }


                        //val userRef = FirebaseFirestore.getInstance().collection(Constant.REPORT)

                        //val id = firebaseUser.uid
                        //val fullName = et_sign_up_full_name.text.toString().trim { it <= ' '}
                        //val email = et_sign_up_email.text.toString().trim { it <= ' '}
                        //val password = et_sign_up_password.text.toString().trim { it <= ' '}

                        /*val user = ModelUser(
                            id = firebaseUser.uid,
                            fullName = binding.etSignUpFullName.text.toString().trim { it <= ' '},
                            email = binding.etSignUpEmail.text.toString().trim { it <= ' '}
                        )*/

                        //FirestoreClass().registerUser(this, user)*/

                        //val user = HashMap<String, Any>()
                        /*user["id"] = firebaseUser.uid
                        user["fullName"] = fullName
                        user["email"] = email
                        user["tipeAduan"] = ""
                        user["tanggalKejadian"] = ""
                        user["lokasi"] = ""
                        user["keterangan"] = ""
                        user["gambar"] = ""
                        user["status"] = "Belum Terkonfirmasi"*/

                        /*userRef.add(user).addOnSuccessListener {
                            Toast.makeText(this, "Registration is Successful !", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                        }*/

                        Toast.makeText(this, "Pendaftaran Berhasil!", Toast.LENGTH_SHORT).show()
                        /*Intent(this@SignUpActivity, SignInActivity::class.java).also { intent ->
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }*/
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

    fun userRegisterSucsess(user: ModelUser) {

        Log.i("email: ", user.email)
        Log.i("fullName", user.fullName)

        val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
        startActivity(intent)

    }
}