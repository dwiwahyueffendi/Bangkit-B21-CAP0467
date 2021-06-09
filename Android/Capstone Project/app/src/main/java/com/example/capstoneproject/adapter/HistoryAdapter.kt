package com.example.capstoneproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.model.ModelUser
import com.example.capstoneproject.databinding.ListItemReportBinding

class HistoryAdapter(private val report: List<ModelUser>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = ListItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(report[position])
    }

    override fun getItemCount(): Int = report.size

    class ViewHolder(val binding: ListItemReportBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(report: ModelUser){
            binding.apply {
                tvFullName.text = StringBuilder(" Oleh: ${report.fullName}")
                tvTime.text = report.waktuKejadian
                tvLocation.text = StringBuilder(" Lokasi: ${report.lokasi}")
                tvDecsription.text = StringBuilder(" Keterangan: ${report.keterangan}")
                tvStatus.text = StringBuilder(" Status: ${report.status}")
            }
        }

    }
}