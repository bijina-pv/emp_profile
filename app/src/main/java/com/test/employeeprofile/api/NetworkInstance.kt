package com.test.employeeprofile.api

import com.google.gson.JsonArray
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkInstance {
    var service:NetworkEndPoint
    companion object Companion{

        var BASE_URL="http://www.mocky.io/v2/"

    }

    init {
        val interceptor = run {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            }
        }
        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        service = retrofit.create(NetworkEndPoint::class.java)

    }
    fun fetchEmployeeDetails(callback: Callback<JsonArray>) {
        val call = service.fetchEmployeeDetails()
        call.enqueue(callback)
    }
}