package com.nin0dev.vendroid.ui.components

import android.graphics.Bitmap
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.io.IOException
import androidx.browser.customtabs.CustomTabsIntent
import java.net.HttpURLConnection
import java.net.URL

class WebviewClient(
	private val webView: WebView,
	private val onLoad: () -> Unit
) : WebViewClient() {
	override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
		try {
			view?.evaluateJavascript(VencordManager.vencord, null)
		}
		catch (e: Exception) {
			Toast.makeText(view?.context, "Couldn't load Vencord, try restarting the app.", Toast.LENGTH_LONG).show()
		}
	}

	override fun onPageFinished(view: WebView?, url: String?) {
		super.onPageFinished(view, url)
		this.addStyles()
		onLoad()
	}

	override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
		if (!request.url.toString().contains("discord.com")) {
			CustomTabsIntent.Builder()
				.setShowTitle(true)
				.setUrlBarHidingEnabled(true)
				.build()
				.launchUrl(view.context, request.url)
			return true
		} else {
			return false
		}
	}

	override fun shouldInterceptRequest(view: WebView, req: WebResourceRequest): WebResourceResponse? {
		val uri = req.url
		if (req.isForMainFrame || req.url.path!!.endsWith(".css")) {
			try {
				return doFetch(req)
			} catch (ex: IOException) {
				//e("Error during shouldInterceptRequest", ex)
			}
		}
		return null
	}

	private fun doFetch(req: WebResourceRequest): WebResourceResponse {
		val url = req.url.toString()
		val conn = URL(url).openConnection() as HttpURLConnection
		conn.setRequestMethod(req.method)
		for ((key, value) in req.requestHeaders) {
			conn.setRequestProperty(key, value)
		}
		val code = conn.getResponseCode()
		val msg = conn.getResponseMessage()
		val headers = conn.headerFields
		val modifiedHeaders = HashMap<String, String>(headers.size)
		for ((key, valueList) in headers) {
			if (key == null) {
				continue
			}
			if (!"Content-Security-Policy".equals(key, ignoreCase = true)) {
				if (valueList != null && valueList.isNotEmpty()) {
					val value = valueList[0]
					modifiedHeaders[key] = value
				}
			}
		}
		if (url.endsWith(".css")) modifiedHeaders["Content-Type"] = "text/css"
		val wong = modifiedHeaders.getOrDefault("Content-Type", "application/octet-stream")
		return WebResourceResponse(wong, "utf-8", code, msg, modifiedHeaders, conn.inputStream)
	}

	private fun addStyles() {
		val css = """
			form[class*="authBox"] {
				padding-top: calc(1em + env(safe-area-inset-top)) !important;
				padding-bottom: calc(1em + env(safe-area-inset-bottom)) !important;
				padding-left: calc(1em + env(safe-area-inset-left)) !important;
				padding-right: calc(1em + env(safe-area-inset-right)) !important;
			}
			[data-layer="base"] {
				padding-top: env(safe-area-inset-top) !important;
				padding-left: env(safe-area-inset-left) !important;
				padding-right: env(safe-area-inset-right) !important;
			}
			[class*="tabBody"] {
				padding-left: env(safe-area-inset-left) !important;
				padding-right: env(safe-area-inset-right) !important;
			}
			form[class*="formWithLoadedChatInput"] {
				padding-left: calc(0.75em + env(safe-area-inset-left)) !important;
				padding-right: calc(0.75em + env(safe-area-inset-right)) !important;
				transition: padding-bottom 0.1s;
				transition-delay: 0.05s;
			}
			form[class*="formWithLoadedChatInput"]:not(:has(textarea:focus-visible)) {
				padding-bottom: env(safe-area-inset-bottom) !important;
			}
			/* TODO: localize aria-label */
			[aria-label="User area"] {
				bottom: env(safe-area-inset-bottom) !important;
			}
		""".trimIndent()
		webView.evaluateJavascript(
			"""
			const style = document.createElement("style")
			style.innerHTML = `$css`
			document.head.appendChild(style)
		""".trimIndent(), null
		)
	}
}
