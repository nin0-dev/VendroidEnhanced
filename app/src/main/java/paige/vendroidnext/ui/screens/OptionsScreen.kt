package paige.vendroidnext.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import paige.vendroidnext.ui.components.forms.Form
import paige.vendroidnext.ui.components.forms.FormScaffold
import paige.vendroidnext.ui.components.forms.Section
import paige.vendroidnext.ui.components.forms.SectionRow
import paige.vendroidnext.ui.components.forms.SectionRowLabel
import paige.vendroidnext.ui.theme.VendroidTheme
import paige.vendroidnext.ui.utils.openUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsScreen() {
	FormScaffold("Options") {
		Form(it) {
			Section("General") {
				SectionRow {
					SectionRowLabel(
						title = "Mods",
						subtitle = "Manage client mods",
						icon = Icons.Rounded.Star,
						tint = Color(0xFFF3BE95)
					)
				}
				SectionRow {
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
					onClick = { context, _ ->
						openUrl(context, "https://github.com/nin0-dev/VendroidEnhanced")
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

@Composable
@Preview(
	showBackground = true,
	showSystemUi = true
)
private fun Preview() {
	VendroidTheme(true) {
		OptionsScreen()
	}
}
