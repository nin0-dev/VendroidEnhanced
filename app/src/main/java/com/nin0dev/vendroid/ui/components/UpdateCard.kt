package com.nin0dev.vendroid.ui.components

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.SecurityUpdate
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.nin0dev.vendroid.utils.Announcement

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun UpdateCard(update: Announcement) {
	val context = LocalContext.current

	Card(
		modifier = Modifier
			.padding(horizontal = 16.dp)
			.padding(top = 8.dp)
			.fillMaxWidth()
	) {
		Column(
			modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
		) {
			Text(
				text = update.title,
				style = MaterialTheme.typography.titleLargeEmphasized
			)
			Text(
				text = update.text,
				modifier = Modifier.padding(top = 4.dp)
			)
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.End
			) {
				Button(
					onClick = {
						context.startActivity(
							Intent()
								.setAction(Intent.ACTION_VIEW)
								.setData("https://github.com/nin0-dev/VendroidEnhanced/releases/latest/download/app-release.apk".toUri())
						)
					}
				) {
					Icon(
						imageVector = Icons.Filled.SecurityUpdate,
						contentDescription = "Update",
						modifier = Modifier.padding(end = 8.dp)
					)
					Text("Update")
				}
			}
		}
	}
}
