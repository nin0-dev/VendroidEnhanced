package com.nin0dev.vendroid.ui.screens

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubble
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.SecurityUpdate
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.nin0dev.vendroid.BuildConfig
import com.nin0dev.vendroid.ui.components.forms.Form
import com.nin0dev.vendroid.ui.components.forms.FormScaffold
import com.nin0dev.vendroid.ui.components.forms.Section
import com.nin0dev.vendroid.ui.components.forms.SectionRow
import com.nin0dev.vendroid.ui.components.forms.SectionRowLabel

@Composable
fun OptionsHomeScreen(
	navigate: (String) -> Unit,
) {
	val context = LocalContext.current
	FormScaffold("Options", {
		(context as? ComponentActivity)?.onBackPressedDispatcher?.onBackPressed()
	}) {
		Form(it) {
			Section("General") {
				SectionRow(
					onClick = {
						navigate("mods")
					}
				) {
					SectionRowLabel(
						title = "Mods",
						subtitle = "Choose which client mod to use",
						icon = Icons.Rounded.Star,
						tint = Color(0xFFF3BE95)
					)
				}
				SectionRow(
					onClick = {
						navigate("vde_settings")
					}
				) {
					SectionRowLabel(
						title = "Vendroid Settings",
						subtitle = "Change Vendroid features",
						icon = Icons.Rounded.Settings,
						tint = Color(0xFFF3ADDF)
					)
				}
				SectionRow(
					onClick = {
						navigate("vde_autoupdate")
					}
				) {
					SectionRowLabel(
						title = "Updater",
						subtitle = "Check updates and configure the VendroidEnhanced update/announcement checker",
						icon = Icons.Rounded.SecurityUpdate,
						tint = Color(0xFFC4FFAD)
					)
				}
			}
			Section("Info") {
				SectionRow {
					SectionRowLabel(
						title = "About",
						subtitle = "VendroidEnhanced ${BuildConfig.VERSION_NAME}${if(BuildConfig.DEBUG) " (Dev)" else ""}",
						icon = Icons.Rounded.Info
					)
				}
				SectionRow(
					onClick = {
						context.startActivity(
							Intent()
								.setAction(Intent.ACTION_VIEW)
								.setData("https://github.com/nin0-dev/VendroidEnhanced".toUri())
						)
					}
				) {
					SectionRowLabel(
						title = "Source Code",
						subtitle = "nin0-dev/VendroidEnhanced",
						icon = Icons.Rounded.Code
					)
				}
				SectionRow(
					onClick = {
						context.startActivity(
							Intent()
								.setAction(Intent.ACTION_VIEW)
								.setData("https://discord.gg/Xb36hWqBGC".toUri())
						)
					}
				) {
					SectionRowLabel(
						title = "Support Server",
						subtitle = "Report issues and discuss VendroidEnhanced here",
						icon = Icons.Rounded.ChatBubble
					)
				}
			}
		}
	}
}
