package com.santanu.android.androidapplication.utils.network

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.text.TextUtils
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*
import kotlin.experimental.and


class NetworkDetailsUtils {
    private val TAG: String = NetworkDetailsUtils::class.java.simpleName

    /**
     * Convert byte array to hex string
     * @param bytes
     * @return
     **/
    @SuppressLint("DefaultLocale")
    internal fun bytesToHex(bytes: ByteArray): String? {
        val stringBuffer: StringBuilder = java.lang.StringBuilder()
        for (idx in bytes.indices) {
            val intVal: Int = (bytes[idx] and 0xff.toByte()).toInt()
            if (intVal < 0x10) {
                stringBuffer.append("0")
            }
            stringBuffer.append(Integer.toHexString(intVal).toUpperCase())
        }
        return stringBuffer.toString()
    }

    /**
     * Get utf8 byte array.
     * @param str
     * @return  array of NULL if error was found
     **/
    internal fun getUTF8Bytes(str: String): ByteArray? {
        return try {
            str.toByteArray(charset("UTF-8"))
        } catch (ex: java.lang.Exception) {
            null
        }
    }

    /**
     * Get IP address from first non-localhost interface
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    @SuppressLint("DefaultLocale")
    internal fun getIPAddress(useIPv4: Boolean=false): String {
        try {
            val interfaces: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (interf in interfaces) {
                val addresses: List<InetAddress> = Collections.list(interf.inetAddresses)
                for (address in addresses) {
                    if (!address.isLoopbackAddress) {
                        val serverAddress: String = address.hostAddress
                        // val isIPv4: Boolean = interAddressUtils.isIPv4Address(serverAddress);
                        val isIPv4: Boolean = serverAddress.indexOf(':') < 0
                        if (useIPv4) {
                            if (isIPv4) return serverAddress
                        } else {
                            if (!isIPv4) {
                                val deslim: Int = serverAddress.indexOf('%') // drop ip6 zone suffix
                                return if (deslim < 0) {
                                    serverAddress.toUpperCase()
                                } else {
                                    serverAddress.substring(0, deslim).toUpperCase()
                                }
                            }
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return ""
    }

    /**
     * Returns MAC address of the given interface name.
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return  mac address or empty string
     */
    internal fun getMACAddress(interfaceName: String?): String {
        try {
            val interfaces: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (interf in interfaces) {
                if (interfaceName != null) {
                    if (!interf.name.equals(interfaceName, ignoreCase = true)) continue
                }
                val mac: ByteArray = interf.hardwareAddress ?: return ""
                val buffer: StringBuilder = StringBuilder()
                for (index in mac.indices) buffer.append(String.format("%02X:", mac[index]))
                if (buffer.isNotEmpty()) {
                    buffer.deleteCharAt(buffer.length - 1)
                }
                return buffer.toString()
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return ""
        /**
        try {
        // this is so Linux hack
        return loadFileAsString("/sys/class/net/" +interfaceName + "/address").toUpperCase().trim();
        } catch (IOException ex) {
        return null;
        }
         **/
    }

    internal fun getLocalIpAddress(): String? {
        try {
            val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf: NetworkInterface = en.nextElement()
                val enumIpAddr: Enumeration<InetAddress> = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.hostAddress
                    }
                }
            }
        } catch (ex: SocketException) {
            ex.printStackTrace()
        }
        return null
    }

    internal fun getNetworkInterfaceIpAddress(): String? {
        try {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val networkInterface = en.nextElement()
                val enumIpAddr = networkInterface.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        val host = inetAddress.getHostAddress()
                        if (!TextUtils.isEmpty(host)) {
                            return host
                        }
                    }
                }
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return null
    }

    private fun getWifiIp(context: Context): String? {
        val mWifiManager: WifiManager? = context.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        if (mWifiManager != null && mWifiManager.isWifiEnabled) {
            val ip = mWifiManager.connectionInfo.ipAddress
            return ((ip and 0xFF).toString() + "." + (ip shr 8 and 0xFF) + "." + (ip shr 16 and 0xFF) + "." + (ip shr 24 and 0xFF))
        }
        return null
    }

    private fun getDeviceIpAddress(context: Context): String? {
        var actualConnectedToNetwork: String? = null
        val connManager: ConnectivityManager? =  context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (connManager != null) {
            val mWifi: NetworkInfo? = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            if (mWifi?.isConnected!!) {
                actualConnectedToNetwork = getWifiIp(context)
            }
        }
        if (TextUtils.isEmpty(actualConnectedToNetwork)) {
            actualConnectedToNetwork = getNetworkInterfaceIpAddress()
        }
        if (TextUtils.isEmpty(actualConnectedToNetwork)) {
            actualConnectedToNetwork = "127.0.0.1"
        }
        return actualConnectedToNetwork
    }
}