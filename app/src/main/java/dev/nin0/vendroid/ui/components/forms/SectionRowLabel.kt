package dev.nin0.vendroid.ui.components.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SectionRowLabel(
	title: String,
	subtitle: String? = null,
	icon: ImageVector? = null,
	tint: Color = Color.LightGray,
) {
	icon?.let {
		Box(
			modifier = Modifier
				.size(38.dp)
				.background(tint, shape = CircleShape),
			contentAlignment = Alignment.Center
		) {
			Icon(
				imageVector = icon,
				contentDescription = null,
				tint = androidx.compose.ui.graphics.lerp(
					Color.Black, tint, 0.45f
				)
			)
		}
	}
	Column {
		Text(
			title,
			style = MaterialTheme.typography.titleMedium,
			color = MaterialTheme.colorScheme.onSurface
		)
		subtitle?.let {
			Text(
				text = subtitle,
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurfaceVariant
			)
		}
	}
}
