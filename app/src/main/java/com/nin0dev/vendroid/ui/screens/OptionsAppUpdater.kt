package com.nin0dev.vendroid.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nin0dev.vendroid.ui.activities.OptionsActivity
import com.nin0dev.vendroid.ui.components.UpdateCard
import com.nin0dev.vendroid.ui.components.forms.Form
import com.nin0dev.vendroid.ui.components.forms.FormScaffold
import com.nin0dev.vendroid.ui.components.forms.Section
import com.nin0dev.vendroid.ui.components.forms.SectionRow
import com.nin0dev.vendroid.ui.components.forms.SectionRowLabel
import com.nin0dev.vendroid.ui.theme.VendroidTheme
import com.nin0dev.vendroid.utils.Announcement
import com.nin0dev.vendroid.utils.DS
import com.nin0dev.vendroid.utils.getUpdates

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun OptionsAppUpdater(
	onBackPressed: () -> Unit = {}
) {
	val context = LocalContext.current
	val checkAppUpdates = DS(context).getBool("checkAppUpdates", true)
	val checkAnnouncements = DS(context).getBool("checkAnnouncements", true)
	val areUpdatesChecked = remember {
		mutableStateOf(false)
	}
	var update = remember {
		mutableStateOf<Announcement?>(null)
	}

	LaunchedEffect(Unit) {
		val updates = getUpdates(context) {
			update.value = it.update
			areUpdatesChecked.value = true
		}
	}
	FormScaffold("App updater", onBackPressed) {
		Form(it) {
			if (areUpdatesChecked.value) {
				if (update.value == null) {
					Box(
						modifier = Modifier.fillMaxWidth()
					) {
						Icon(
							imageVector = Icons.Filled.CheckCircleOutline,
							contentDescription = "Check",
							modifier = Modifier
								.size(128.dp)
								.padding(16.dp)
								.align(Alignment.Center),
							tint = MaterialTheme.colorScheme.onPrimaryContainer
						)
					}
					Text(
						"You're up to date",
						modifier = Modifier.fillMaxWidth(),
						style = MaterialTheme.typography.headlineSmall,
						textAlign = TextAlign.Center
					)
				}
				else {
					UpdateCard(update.value!!)
				}
			}
			else {
				Box(
					modifier = Modifier.fillMaxWidth()
				) {
					LoadingIndicator(
						modifier = Modifier
							.size(128.dp)
							.align(Alignment.Center)
					)
				}
				Text(
					"Checking for updates...",
					modifier = Modifier.fillMaxWidth(),
					style = MaterialTheme.typography.headlineSmall,
					textAlign = TextAlign.Center
				)
			}
			Section(title = "At app start") {
				SectionRow(
					horizontalArrangement = Arrangement.SpaceBetween,
					onClick = {
						DS(context).setBool("checkAppUpdates", !checkAppUpdates.value)
					}
				) {
					SectionRowLabel(
						title = "Check for updates"
					)
					Switch(
						checked = checkAppUpdates.value,
						onCheckedChange = null
					)
				}
				SectionRow(
					horizontalArrangement = Arrangement.SpaceBetween,
					onClick = {
						DS(context).setBool("checkAnnouncements", !checkAnnouncements.value)
					}
				) {
					SectionRowLabel(
						title = "Check for announcements"
					)
					Switch(
						checked = checkAnnouncements.value,
						onCheckedChange = null
					)
				}
			}
			FilledTonalButton(
				modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
				onClick = {

				}
			) {
				Icon(
					imageVector = Icons.Filled.Handshake,
					contentDescription = "Privacy",
					modifier = Modifier.padding(end = 8.dp)
				)
				Text("See how your data is managed...")
			}
		}
	}
}

@Composable
@Preview(
	showBackground = true,
	showSystemUi = true
)
private fun Preview() {
	VendroidTheme(true) {
		OptionsAppUpdater()
	}
}
