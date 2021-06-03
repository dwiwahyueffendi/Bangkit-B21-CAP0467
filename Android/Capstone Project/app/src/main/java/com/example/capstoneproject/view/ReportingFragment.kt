package com.example.capstoneproject.view

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capstoneproject.databinding.FragmentReportingBinding
import com.example.capstoneproject.model.ModelUser
import com.example.capstoneproject.utils.Constant
import com.example.capstoneproject.utils.DateUtils
import com.google.firebase.firestore.FirebaseFirestore

/*class ReportingFragment : Fragment() {

    private lateinit var binding: FragmentReportingBinding
    private lateinit var modelUser: ModelUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentReportingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            uploadReporting()
        }

        //val sharedPreferences = context.getSharedPreferences(Constant.MYREPORT_PREFERENCE, Context.MODE_PRIVATE)
        //val username = sharedPreferences.getString(Constant.LOGGED_IN_USERNAME, "")!!
    }

    private fun validatingReporting(): Boolean {
        return when{
            TextUtils.isEmpty(binding.etLokasi.text.toString().trim{ it <= ' '}) -> {
                binding.etLokasi.error = "Lokasi harus di isi"
                binding.etLokasi.requestFocus()
                false
            }
            TextUtils.isEmpty(binding.etKeterangan.text.toString().trim{ it <= ' '}) -> {
                binding.etKeterangan.error = "Keterangan harus di isi"
                binding.etKeterangan.requestFocus()
                false
            }
            else -> true
        }
    }
    
    private fun uploadReporting() {
        if(validatingReporting()){
            if (binding.cbJalanRusak)

            val id = modelUser.id
            val fullname = modelUser.fullName
            val email = modelUser.email
            val waktuKejadian = DateUtils.DateUtils.getNowDate()
            val keterangan = binding.etKeterangan.text.toString().trim { it <= ' '}
            val lokasi = binding.etLokasi.text.toString().trim { it <= ' '}

            //val idUser = FirestoreClass().getCurrentUserID()

            val userRef = FirebaseFirestore.getInstance().collection(Constant.REPORT)

            val user = ModelUser(
                id = id,
                //tipeAduan = tipeAduan,
                waktuKejadian = waktuKejadian,
                keterangan = keterangan,
                lokasi = lokasi
            )

            userRef.add(user).addOnSuccessListener {

                //Toast.makeText(this@ReportingFragment, "Registration is Successful !", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    //Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ReportingFragment().apply {
                arguments = Bundle().apply {}

            }
    }
}*/