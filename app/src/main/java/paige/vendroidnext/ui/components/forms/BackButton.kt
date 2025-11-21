package paige.vendroidnext.ui.components.forms

import android.view.SoundEffectConstants
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp

@Composable
fun BackButton() {
	val view = LocalView.current
	val context = LocalContext.current
	IconButton(
		onClick = {
			view.playSoundEffect(SoundEffectConstants.NAVIGATION_LEFT)
			(context as? ComponentActivity)?.onBackPressedDispatcher?.onBackPressed()
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
