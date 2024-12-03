package com.nin0dev.vendroid

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
import com.nin0dev.vendroid.Logger.e
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class VWebviewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        val url = request.url
        // Check if the URL is a valid discord URL or blank, otherwise open in external browser
        if ("discord.com" == url.authority || "about:blank" == url.toString()) {
            return false
        }
        val intent = Intent(Intent.ACTION_VIEW, url)
        view.context.startActivity(intent)
        return true
    }

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        try {
            // Evaluate the Vencord runtime scripts if available
            HttpClient.VencordRuntime?.let { view.evaluateJavascript(it, null) }
            HttpClient.VencordMobileRuntime?.let { view.evaluateJavascript(it, null) }
        } catch (e: Exception) {
            // Handle any errors that occur while loading Vencord
            Toast.makeText(view.context, "Couldn't load Vencord, try restarting the app.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onPageFinished(view: WebView, url: String) {
        // Make the WebView visible once the page is loaded
        view.visibility = View.VISIBLE
        super.onPageFinished(view, url)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun shouldInterceptRequest(view: WebView, req: WebResourceRequest): WebResourceResponse? {
        val uri = req.url
        // Only intercept requests for the main frame or .css files
        if (req.isForMainFrame || req.url.path!!.endsWith(".css")) {
            try {
                return doFetch(req)
            } catch (ex: IOException) {
                // Log any error that occurs during the fetch process
                e("Error intercepting request", ex)
            }
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun doFetch(req: WebResourceRequest): WebResourceResponse {
        val url = req.url.toString()
        // Open a connection to fetch the resource
        val conn = URL(url).openConnection() as HttpURLConnection
        conn.requestMethod = req.method
        // Add the request headers to the connection
        for ((key, value) in req.requestHeaders) {
            conn.setRequestProperty(key, value)
        }
        // Get the response from the server
        val code = conn.responseCode
        val msg = conn.responseMessage
        val headers = conn.headerFields
        val modifiedHeaders = HashMap<String, String>(headers.size)

        // Process the response headers, excluding certain ones like Content-Security-Policy
        for ((key, valueList) in headers) {
            if (key != null && !"Content-Security-Policy".equals(key, ignoreCase = true) && valueList.isNotEmpty()) {
                modifiedHeaders[key] = valueList[0]
            }
        }

        // If it's a .css file, ensure the correct Content-Type header is set
        if (url.endsWith(".css")) modifiedHeaders["Content-Type"] = "text/css"

        // Return the response with the modified headers and content
        val contentType = modifiedHeaders.getOrDefault("Content-Type", "application/octet-stream")
        return WebResourceResponse(contentType, "utf-8", code, msg, modifiedHeaders, conn.inputStream)
    }
}
