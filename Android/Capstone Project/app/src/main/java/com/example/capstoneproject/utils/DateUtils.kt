package com.example.capstoneproject.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun getNowDate(): String {
        val sdf = SimpleDateFormat("yyyy/MM/dd H:mm:ss", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
        val netDate = Date()
        return sdf.format(netDate)
    }

}