package com.santanu.android.androidapplication.utils.network

import android.content.Context
import android.net.wifi.WifiManager

class WiFiNetworkUtils(private val context: Context) {

    internal enum class WifiState {
        Disabled, /* WiFi is not enabled */
        EnabledNotConnected, /* WiFi is enabled but we are not connected to any WiFi network */
        Connected, /* Connected to a WiFi network */
    }

    /** Get the current state of the WiFi network **/
    internal fun getWifiState() : WifiState {
        val wifiManager : WifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return if (wifiManager.isWifiEnabled) {
            if (wifiManager.connectionInfo.bssid != null){
                WifiState.Connected
            }
            else {
                WifiState.EnabledNotConnected
            }
        } else {
            WifiState.Disabled
        }
    }

    /** Returns true if we are connected to a WiFi network **/
    internal fun isWiFiConnected() : Boolean {
        return (getWifiState() == WifiState.Connected)
    }




}