package com.nin0dev.vendroid.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nin0dev.vendroid.ui.screens.OptionsScreen
import com.nin0dev.vendroid.ui.theme.VendroidTheme

class OptionsActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		enableEdgeToEdge()
		super.onCreate(savedInstanceState)
		setContent {
			VendroidTheme {
				OptionsScreen()
			}
		}
	}
}
