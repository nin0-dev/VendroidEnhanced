package com.nin0dev.vendroid.ui.activities

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nin0dev.vendroid.ui.screens.WebScreen
import com.nin0dev.vendroid.ui.theme.VendroidTheme
import androidx.core.graphics.toColorInt

class VendroidActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		enableEdgeToEdge(
			statusBarStyle = SystemBarStyle.dark(
				scrim = "#FF0000".toColorInt()
			),
			navigationBarStyle = SystemBarStyle.dark(
				scrim = "#FF0000".toColorInt()
			)
		)
		super.onCreate(savedInstanceState)
		setContent {
			VendroidTheme {
				WebScreen()
			}
		}
	}
}
