package com.santanu.android.androidapplication.data.api

import retrofit2.Retrofit

object ServicesInstance {

    internal fun<T> getServicesInstance(mRetrofit: Retrofit, mServices: Class<T>) : T {
        return mRetrofit.create(mServices)
    }

}