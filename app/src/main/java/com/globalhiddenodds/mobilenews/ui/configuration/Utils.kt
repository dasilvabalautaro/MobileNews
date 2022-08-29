package com.globalhiddenodds.mobilenews.ui.configuration

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

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

    fun diffDate(created: String?): String {
        if (created != null && created.length >= 19) {
            val hitDate = created.substring(0, 19)
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
            val textNow = LocalDateTime.now().format(formatter)
            val timeNow = LocalDateTime.parse(textNow, formatter)
            val timeCreate = LocalDateTime.parse(hitDate, formatter)
            val millNow = timeNow.toInstant(ZoneOffset.UTC).toEpochMilli()
            val millCreate = timeCreate.toInstant(ZoneOffset.UTC).toEpochMilli()
            val diff = millNow - millCreate
            val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
            val hours = TimeUnit.MILLISECONDS.toHours(diff)
            val days = TimeUnit.MILLISECONDS.toDays(diff)

            return  when {
                days > 1 -> days.toString() + "days"
                hours in 1..23 -> hours.toString() + "h"
                days == 1L -> "Yesterday"
                minutes < 60 -> minutes.toString() + "m"
                else -> ""
            }
        }
        return ""
    }
}