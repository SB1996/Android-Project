package com.santanu.android.androidapplication.utils.network

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Build
import androidx.lifecycle.LiveData

class NetworkStatus(private val mContext : Context) : LiveData<Boolean>() {
    private val TAG: String = NetworkStatus::class.java.simpleName

    private lateinit var mNetworkCallback: ConnectivityManager.NetworkCallback
    private val mConnectivityManager : ConnectivityManager = mContext.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager

    private val networkReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateNetworkConnection()
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @Suppress("DEPRECATION")
    override fun onActive() {
        super.onActive()
        updateNetworkConnection()
        when {
            /** run on device from api-24(Nougat) to higher api **/
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
               mConnectivityManager.registerDefaultNetworkCallback(connectivityManagerCallback())
            }

            /** run on device from api-21(Lollipop) to higher api-23(Marshmallow) **/
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                lollipopNetworkRequest()
            }

            else -> {
                mContext.registerReceiver(
                    networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                )
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onInactive() {
         super.onInactive()
         /** run on device from api-21(Lollipop) to higher api **/
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             try {
                 mConnectivityManager.unregisterNetworkCallback(connectivityManagerCallback())
             } catch (ex: Exception) {
                 ex.printStackTrace()
             }
         } else {
             mContext.unregisterReceiver(networkReceiver)
         }
    }

    @SuppressLint("MissingPermission")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun lollipopNetworkRequest() {
        val requestBuilder = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)

        mConnectivityManager.registerNetworkCallback(
            requestBuilder.build(), connectivityManagerCallback()
        )
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun connectivityManagerCallback() : ConnectivityManager.NetworkCallback {

        /** run on device from api-21(Lollipop) to higher api **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mNetworkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    super.onLost(network)
                    postValue(false)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    postValue(true)
                }
            }
            return mNetworkCallback
        } else {
            /** if device run on from api-21(Lollipop)['exclude'] to lower api **/
            /** throw "IllegalAccessError" exception [otherwise you handle manually
             ** unless exception occur] **/
            throw IllegalAccessError("Illegal Access Error")
        }

    }

    @Suppress("DEPRECATION")
    @SuppressLint("MissingPermission", "NewApi")
    private fun updateNetworkConnection() {
        val activeNetwork: NetworkInfo? = mConnectivityManager.activeNetworkInfo
        postValue(activeNetwork?.isConnected == true)
    }

}