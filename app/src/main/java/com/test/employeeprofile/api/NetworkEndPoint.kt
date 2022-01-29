package com.test.employeeprofile.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.*
import java.util.concurrent.TimeUnit

public interface NetworkEndPoint {
    @GET("5d565297300000680030a986")
    fun fetchEmployeeDetails(): Call<JsonArray>
}