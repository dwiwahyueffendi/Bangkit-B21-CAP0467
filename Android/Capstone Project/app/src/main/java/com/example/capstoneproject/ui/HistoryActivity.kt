package com.example.capstoneproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.adapter.HistoryAdapter
import com.example.capstoneproject.databinding.ActivityHistoryBinding
import com.example.capstoneproject.databinding.ListItemReportBinding
import com.example.capstoneproject.model.ModelUser
import com.example.capstoneproject.utils.Constant
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getReport()
    }

    private fun getReport() {
        Firebase.firestore.collection(Constant.REPORT)
            .get()
            .addOnSuccessListener {
                var listReport: ArrayList<ModelUser> = ArrayList()
                listReport.clear()

                for( document in it){
                    listReport.add((ModelUser(
                        document.id,
                        document.data["fullName"] as String,
                        document.data["email"] as String,
                        document.data["waktuKejadian"] as String,
                        document.data["lokasi"] as String,
                        document.data["keterangan"] as String,
                        document.data["gambar"] as String,
                        document.data["status"] as String
                    )))
                }

                var historyAdapter = HistoryAdapter(listReport)
                binding.apply {
                    rvReport.setHasFixedSize(true)
                    rvReport.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    rvReport.adapter = historyAdapter
                }
            }
            .addOnFailureListener {
                Log.v("", "Gagal Mengambil Data")
            }
    }
}