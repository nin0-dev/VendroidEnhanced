package paige.vendroidnext.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import paige.vendroidnext.ui.screens.WebScreen
import paige.vendroidnext.ui.theme.VendroidTheme

class VendroidActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			VendroidTheme {
				WebScreen()
			}
		}
	}
}
