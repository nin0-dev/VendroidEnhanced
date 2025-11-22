package dev.nin0.vendroid.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.nin0.vendroid.ui.components.forms.Form
import dev.nin0.vendroid.ui.components.forms.FormScaffold
import dev.nin0.vendroid.ui.theme.VendroidTheme

@Composable
fun OptionsVDEScreen(
	onBackPressed: () -> Unit = {}
) {
	FormScaffold("Vendroid settings", onBackPressed) {
		Form(it) {

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
		OptionsVDEScreen()
	}
}
