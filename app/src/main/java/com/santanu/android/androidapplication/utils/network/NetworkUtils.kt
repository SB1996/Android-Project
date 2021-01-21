package com.santanu.android.androidapplication.utils.network

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtils {

    internal fun isNetworkConnected(context: Context): Boolean {
        val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}