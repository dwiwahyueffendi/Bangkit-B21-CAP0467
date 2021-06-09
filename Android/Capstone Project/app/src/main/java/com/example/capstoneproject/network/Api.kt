package com.example.capstoneproject.network

import com.example.capstoneproject.data.ReportResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("predict/img/{data}")
    fun createPost(
        @Field("data") data: String
    ): Call<ReportResponse>
}