package com.santanu.android.androidapplication.data.vm

import android.app.Application
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import com.santanu.android.androidapplication.data.db.ApplicationDatabase
import retrofit2.Retrofit

class MainViewModel @ViewModelInject constructor(
    application: Application,
    val mRetrofit: Retrofit,
    val mApplicationDatabase: ApplicationDatabase
) : AndroidViewModel(application), Observable {
    private val TAG: String = MainViewModel::class.java.simpleName



    override fun addOnPropertyChangedCallback(callback: OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: OnPropertyChangedCallback?) {

    }
}