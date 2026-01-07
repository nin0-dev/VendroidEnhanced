package com.nin0dev.vendroid.ui.components

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.nin0dev.vendroid.ui.activities.OptionsActivity

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Webview(
	webViewState: MutableState<WebView?>
) {
	var initialised by remember { mutableStateOf(false) }

	var cb by remember { mutableStateOf<ValueCallback<Array<Uri>>?>(null) }
	val launcher =
		rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
			if (res.resultCode == Activity.RESULT_OK) {
				val uri = res.data?.data
				if (uri != null) cb?.onReceiveValue(arrayOf(uri))
				else cb?.onReceiveValue(null)
			} else cb?.onReceiveValue(null)
		}

	val context = LocalContext.current

	AndroidView(
		factory = {
			WebView(it).apply {
				WebView.setWebContentsDebuggingEnabled(true)
				webViewState.value = this
				this.settings.javaScriptEnabled = true
				this.settings.allowFileAccess = true
				this.settings.builtInZoomControls = true
				this.settings.displayZoomControls = false
				this.settings.domStorageEnabled = true
				this.layoutParams = ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT
				)
				this.webViewClient = WebviewClient(
					webView = this,
					onLoad = {
						initialised = true
					}
				)
				this.webChromeClient = object : WebChromeClient() {
					override fun onShowFileChooser(
						webView: WebView?,
						filePathCallback: ValueCallback<Array<Uri>>?,
						fileChooserParams: FileChooserParams?
					): Boolean {
						cb = filePathCallback

						fileChooserParams
							?.createIntent()
							?.let { intent -> launcher.launch(intent) }

						return true
					}
				}
				this.loadUrl("https://discord.com/app")
			}
		}
	)
	AnimatedVisibility(
		visible = !initialised,
		modifier = Modifier
			.background(MaterialTheme.colorScheme.surfaceContainer)
			.fillMaxSize()
	) {
		Box {
			LoadingIndicator(
				modifier = Modifier
					.size(128.dp)
					.align(Alignment.Center)
			)
			TextButton(
				onClick = {
					val intent = Intent(context, OptionsActivity::class.java)
					context.startActivity(intent)
				},
				modifier = Modifier
					.align(Alignment.BottomCenter)
					.padding(bottom = 32.dp)
			) {
				Text("Options")
			}
		}
	}
}
