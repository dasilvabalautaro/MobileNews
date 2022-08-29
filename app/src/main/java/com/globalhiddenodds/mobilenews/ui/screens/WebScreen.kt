package com.globalhiddenodds.mobilenews.ui.screens

import android.annotation.SuppressLint
import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebBody(urlRoute: String?) {
    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                WebView(it).apply {
                    settings.javaScriptEnabled = true
                    settings.cacheMode = WebSettings.LOAD_DEFAULT
                    settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.domStorageEnabled = true
                    webViewClient = object : WebViewClient() {
                        override fun onReceivedSslError(
                            view: WebView?,
                            handler: SslErrorHandler?,
                            error: SslError?
                        ) {
                            super.onReceivedSslError(view, handler, error)
                            if (error.toString() == "SSLError") {
                                handler?.cancel()
                            }
                        }
                    }
                }
            },
            update = {
                it.clearCache(true)
                it.clearHistory()
                it.clearMatches()
                try {
                    it.loadUrl(urlRoute!!)
                } catch (ex: Exception) {
                    println(ex.message)
                }
            }
        )
    }
}