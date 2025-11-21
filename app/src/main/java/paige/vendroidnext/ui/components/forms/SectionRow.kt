package paige.vendroidnext.ui.components.forms

import android.content.Context
import android.view.SoundEffectConstants
import android.view.View
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
	onClick: ((context: Context, view: View) -> Unit)? = null,
	content: @Composable () -> Unit
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
			onClick?.invoke(context, view)
		},
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier.padding(16.dp),
			horizontalArrangement = Arrangement.spacedBy(12.dp)
		) {
			content()
		}
	}
}
