package com.santanu.android.androidapplication.data.api.services

import retrofit2.http.GET

interface ApiService {

    @GET("")
    fun getData()
}