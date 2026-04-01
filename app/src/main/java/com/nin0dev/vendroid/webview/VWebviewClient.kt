package com.nin0dev.vendroid.webview

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class VWebviewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        val url = request.url
        if ("web.fluxer.app" == url.authority || "about:blank" == url.toString()) {
            return false
        }
        val intent = Intent(Intent.ACTION_VIEW, url)
        view.context.startActivity(intent)
        return true
    }

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        try {
            HttpClient.VencordRuntime?.let { view.evaluateJavascript(it, null) }
            HttpClient.VencordMobileRuntime?.let { view.evaluateJavascript(it, null) }
        }
        catch (e: Exception) {
            Toast.makeText(view.context, "Couldn't load Vencord, try restarting the app.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onPageFinished(view: WebView, url: String) {
        view.visibility = View.VISIBLE
        super.onPageFinished(view, url)
    }
}
