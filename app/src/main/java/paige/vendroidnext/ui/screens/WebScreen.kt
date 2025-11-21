package paige.vendroidnext.ui.screens

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
import paige.vendroidnext.ui.components.Webview

@Composable
fun WebScreen() {
	val context = LocalContext.current
	val webViewState = remember { mutableStateOf<WebView?>(null) }

	BackHandler {
		if (webViewState.value?.canGoBack() == true)
			webViewState.value?.goBack()
		else (context as? Activity)?.finish()
	}
	Webview(webViewState)

	val dlg = remember { mutableStateOf(true) }
	when {
		dlg.value -> {
			AlertDialog(
				title = {
					Text("Warning")
				},
				text = {
					Text("This is a work in progress, many things are subject to change")
				},
				onDismissRequest = {
					dlg.value = false
				},
				confirmButton = {
					TextButton(
						onClick = {
							dlg.value = false
						}
					) {
						Text("Ok")
					}
				},
			)
		}
	}
}
