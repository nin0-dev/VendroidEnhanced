package com.nin0dev.vendroid.ui.screens

import android.app.Activity
import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.nin0dev.vendroid.ui.components.Webview

@Composable
fun WebScreen() {
	val context = LocalContext.current
	val webViewState = remember { mutableStateOf<WebView?>(null) }

	BackHandler {
		if (webViewState.value?.canGoBack() == true)
			webViewState.value?.goBack()
		else (context as? Activity)?.finish()
	}
	Webview(LocalContext.current, webViewState)
}
