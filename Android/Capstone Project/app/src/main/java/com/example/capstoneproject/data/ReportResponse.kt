package com.example.capstoneproject.data

data class ReportResponse(
    val data: String,
    val predClass: Int,
    val className: String,
    val percentage: Float,
    val status: Int,
    val report: String
)