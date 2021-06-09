package com.example.capstoneproject.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    /*companion object{
        fun getReport(): Api {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://34.101.200.75:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiInstance = retrofit.create(Api::class.java)
            return apiInstance
        }
    }*/

    companion object {
        fun provideOkhttpClient() : OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build()
        }

        fun getReport(): Api {
            /*val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()*/
            val retrofit = Retrofit.Builder()
                .baseUrl("http://34.101.200.75:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkhttpClient())
                .build()

            return retrofit.create(Api::class.java)
        }
    }
}