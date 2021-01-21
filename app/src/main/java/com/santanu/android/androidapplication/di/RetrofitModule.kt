package com.santanu.android.androidapplication.di

import com.santanu.android.androidapplication.data.api.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit {
        return RetrofitInstance.getRetrofitInstance()
    }
}