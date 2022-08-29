package com.globalhiddenodds.mobilenews.ui.configuration

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

//Pattern Singleton
object Utils {
    fun isConnect(context: Context): Boolean {
        try {
            val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as
                    ConnectivityManager
            val network = connectivityManager.activeNetwork ?:
            return false
            val activeNetwork = connectivityManager
                .getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } catch (e: Exception) {
            return false
        }
    }

}