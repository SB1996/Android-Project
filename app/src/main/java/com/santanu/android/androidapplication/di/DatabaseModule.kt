package com.santanu.android.androidapplication.di

import android.content.Context
import com.santanu.android.androidapplication.data.db.ApplicationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule() {

    @Singleton
    @Provides
    fun provideApplicationDatabase(@ApplicationContext mContext: Context) : ApplicationDatabase {
        return ApplicationDatabase.getInstance(mContext)
    }
}