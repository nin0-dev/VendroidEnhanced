package dev.nin0.vendroid.ui.components.forms

import android.view.SoundEffectConstants
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp

@Composable
fun BackButton(
	onBackPressed: () -> Unit
) {
	val view = LocalView.current
	var clicked by remember { mutableStateOf(false) }
	IconButton(
		onClick = {
			view.playSoundEffect(SoundEffectConstants.CLICK)
			if (!clicked) {
				clicked = true
				onBackPressed()
			}
		},
		shape = CircleShape,
		colors = IconButtonDefaults.iconButtonColors(
			containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
			contentColor = MaterialTheme.colorScheme.onSurfaceVariant
		),
		modifier = Modifier
			.padding(start = 12.dp, end = 8.dp)
	) {
		Icon(
			imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
			contentDescription = null
		)
	}
}
