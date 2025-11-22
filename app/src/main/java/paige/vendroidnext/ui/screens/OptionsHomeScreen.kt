package paige.vendroidnext.ui.screens

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import paige.vendroidnext.ui.components.forms.Form
import paige.vendroidnext.ui.components.forms.FormScaffold
import paige.vendroidnext.ui.components.forms.Section
import paige.vendroidnext.ui.components.forms.SectionRow
import paige.vendroidnext.ui.components.forms.SectionRowLabel

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
						subtitle = "Manage client mods",
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
			}
			Section("Info") {
				SectionRow {
					SectionRowLabel(
						title = "About",
						subtitle = "not so stable 1.0.0",
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
			}
		}
	}
}
