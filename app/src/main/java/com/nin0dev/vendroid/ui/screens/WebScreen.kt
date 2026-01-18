package com.nin0dev.vendroid.ui.screens

import android.app.Activity
import android.view.Window
import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SecurityUpdate
import androidx.compose.material.icons.filled.SubdirectoryArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.nin0dev.vendroid.ui.components.UpdateCard
import com.nin0dev.vendroid.ui.components.Webview
import com.nin0dev.vendroid.utils.DS
import com.nin0dev.vendroid.utils.UpdateData
import com.nin0dev.vendroid.utils.getUpdates

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WebScreen() {
	val context = LocalContext.current
	val webViewState = remember { mutableStateOf<WebView?>(null) }
	val sheetState = rememberModalBottomSheetState()
	var showBottomSheet = remember { mutableStateOf(false) }
	val updateResponse = remember { mutableStateOf<UpdateData?>(null) }

	LaunchedEffect(Unit) {
		DS(context).getNRBool("checkAppUpdates", true) { cau ->
			DS(context).getNRBool("checkAnnouncements", true) { cann ->
				if (cau || cann) {
					getUpdates(context) { updates ->
						updateResponse.value = updates
						showBottomSheet.value = true
					}
				}
			}
		}
	}

	BackHandler {
		if (webViewState.value?.canGoBack() == true)
			webViewState.value?.goBack()
		else (context as? Activity)?.finish()
	}
	Box(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeContent)) {
		Webview(LocalContext.current, webViewState)
	}

	if (showBottomSheet.value) {
		ModalBottomSheet(
			onDismissRequest = {
				showBottomSheet.value = false
			},
			sheetState = sheetState
		) {
			if (updateResponse.value != null) {
				Column(modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth()) {
					if (updateResponse.value?.announcements != null) {
						updateResponse.value?.announcements?.size?.let {
							if (it > 0) {
								Text(
									text = "Unread announcements",
									style = MaterialTheme.typography.headlineSmallEmphasized,
									fontWeight = FontWeight.W500,
									modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
								)
								for (annc in updateResponse.value!!.announcements) {
									Card(
										modifier = Modifier
											.padding(horizontal = 16.dp)
											.padding(top = 8.dp)
											.fillMaxWidth()
									) {
										Row(
											modifier = Modifier.padding(
												horizontal = 16.dp,
												vertical = 12.dp
											),
											verticalAlignment = Alignment.CenterVertically
										) {
											Text(
												text = annc.title,
												modifier = Modifier
													.weight(1f)
													.padding(end = 8.dp),
												fontSize = 21.sp,
												maxLines = 1,
												overflow = TextOverflow.Ellipsis
											)

											FilledTonalButton(onClick = { /* read action */ }) {
												Icon(
													imageVector = Icons.Filled.SubdirectoryArrowRight,
													contentDescription = "Read",
													modifier = Modifier.padding(end = 8.dp)
												)
												Text("Read")
											}
										}
									}
								}
							}
						}
					}
					if (updateResponse.value?.update != null) {
						Text(
							text = "App update available",
							style = MaterialTheme.typography.headlineSmallEmphasized,
							fontWeight = FontWeight.W500,
							modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
						)
						UpdateCard(updateResponse.value!!.update!!)
					}
				}
			}
		}
	}
}
