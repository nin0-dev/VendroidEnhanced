package dev.nin0.vendroid.ui.components

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.browser.customtabs.CustomTabsIntent

class WebviewClient(
	private val webView: WebView,
	private val onLoad: () -> Unit
) : WebViewClient() {
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
