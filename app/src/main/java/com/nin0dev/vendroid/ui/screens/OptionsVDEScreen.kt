package com.nin0dev.vendroid.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nin0dev.vendroid.ui.components.forms.Form
import com.nin0dev.vendroid.ui.components.forms.FormScaffold
import com.nin0dev.vendroid.ui.theme.VendroidTheme

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
