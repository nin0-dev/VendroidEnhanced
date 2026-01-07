package com.nin0dev.vendroid.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nin0dev.vendroid.ui.screens.WebScreen
import com.nin0dev.vendroid.ui.theme.VendroidTheme

class VendroidActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			VendroidTheme {
				WebScreen()
			}
		}
	}
}
