package com.example.capstoneproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ModelUser (
    var id: String = "",
    var fullName: String = "",
    var email: String = "",
    val waktuKejadian: String = "",
    val lokasi: String = "",
    val keterangan: String = "",
    val gambar: String = "",
    val status: String = "Belum Terkonfirmasi",
): Parcelable