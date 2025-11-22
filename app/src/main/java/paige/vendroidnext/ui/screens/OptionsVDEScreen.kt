package paige.vendroidnext.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import paige.vendroidnext.ui.components.forms.Form
import paige.vendroidnext.ui.components.forms.FormScaffold
import paige.vendroidnext.ui.theme.VendroidTheme

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
