package com.santanu.android.androidapplication.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule() {


    @Singleton
    @Provides
    fun provideContext(@ApplicationContext mContext: Context) : Context? {
        return mContext.applicationContext
    }
}