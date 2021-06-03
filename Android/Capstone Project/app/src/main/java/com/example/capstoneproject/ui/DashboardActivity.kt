package com.example.capstoneproject.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.databinding.ActivityDashboardBinding

class DashboardActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setInformasi()
    }

    private fun setInformasi() {
        binding.apply {
            btnSatudata.setOnClickListener {
                val url = "https://data.go.id"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }

            btnInfoCovid.setOnClickListener {
                val url = "https://covid19.go.id/"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }

            btnInfoVaksinasi.setOnClickListener {
                val url = "https://pedulilindungi.id"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }

            cvLaporkan.setOnClickListener{
                startActivity(Intent(applicationContext, ReportActivity::class.java))
            }

            cvAktivitas.setOnClickListener {
                startActivity(Intent(applicationContext, HistoryActivity::class.java))
            }
        }
    }
}