package com.santanu.android.androidapplication.utils.session

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager private constructor(){
    companion object {
        private val sharePref = SharedPrefManager()
        private lateinit var sharedPreferences: SharedPreferences


        fun getInstance(context: Context): SharedPrefManager {
            if (!Companion::sharedPreferences.isInitialized) {
                synchronized(SharedPrefManager::class.java) {
                    if (!Companion::sharedPreferences.isInitialized) {
                        sharedPreferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
                    }
                }
            }
            return sharePref
        }
    }


    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

}