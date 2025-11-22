package paige.vendroidnext.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import paige.vendroidnext.ui.screens.OptionsScreen
import paige.vendroidnext.ui.theme.VendroidTheme

class OptionsActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			VendroidTheme {
				OptionsScreen()
			}
		}
	}
}
