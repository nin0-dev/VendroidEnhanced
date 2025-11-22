package dev.nin0.vendroid.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.nin0.vendroid.ui.screens.OptionsScreen
import dev.nin0.vendroid.ui.theme.VendroidTheme

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
