package com.santanu.android.androidapplication.data.api

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    private val TAG: String = RetrofitInstance::class.java.simpleName

    companion object {
        private const val BASE_URL: String = "https://www.google.co.in/"
        private const val API_KEY: String = ""

        private val mHttpLoggingInterceptor: HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

        private val mApiKeyInterceptor = Interceptor {
            val url = it.request().url
                .newBuilder()
                .addQueryParameter("key", API_KEY)
                .build()
            val newRequest = it.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor it.proceed(newRequest)
        }

        private val mOkHttpClient: OkHttpClient = OkHttpClient.Builder().apply {
            if (API_KEY.isNotEmpty()) {
                this.addInterceptor(mApiKeyInterceptor)
            }
            this.addInterceptor(mHttpLoggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
        }.build()

        internal fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }
    }
}