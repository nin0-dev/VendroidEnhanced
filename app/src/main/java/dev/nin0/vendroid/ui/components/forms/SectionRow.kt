package dev.nin0.vendroid.ui.components.forms

import android.view.SoundEffectConstants
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp

@Composable
fun SectionRow(
	modifier: Modifier = Modifier,
	onClick: (() -> Unit)? = null,
	horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(12.dp),
	content: @Composable () -> Unit,
) {
	val context = LocalContext.current
	val view = LocalView.current
	Card(
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceBright
		),
		shape = MaterialTheme.shapes.extraSmall,
		modifier = modifier.fillMaxWidth(),
		onClick = {
			view.playSoundEffect(SoundEffectConstants.CLICK)
			onClick?.invoke()
		},
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			horizontalArrangement = horizontalArrangement
		) {
			content()
		}
	}
}
