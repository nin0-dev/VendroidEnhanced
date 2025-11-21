package paige.vendroidnext.ui.components.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Section(
	title: String? = null,
	content: @Composable () -> Unit
) {
	Column(modifier = Modifier.padding(16.dp)) {
		title?.let {
			Text(
				title,
				style = MaterialTheme.typography.titleSmall,
				modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
			)
		}
		Column(
			modifier = Modifier.clip(MaterialTheme.shapes.largeIncreased),
			verticalArrangement = Arrangement.spacedBy(2.dp)
		) {
			content()
		}
	}
}

data class SectionRowColours(
	val iconBackground: Color,
	val iconForeground: Color
) {
	companion object {
		@Composable
		fun default(
			iconBackground: Color = MaterialTheme.colorScheme.secondary,
			iconForeground: Color = MaterialTheme.colorScheme.onSecondary
		): SectionRowColours {
			return SectionRowColours(
				iconBackground,
				iconForeground
			)
		}
	}
}

data class SectionRowTypography(
	val titleStyle: TextStyle,
	val subtitleStyle: TextStyle,
	val subtitleFontFamily: FontFamily?,
) {
	companion object {
		@Composable
		fun default(
			titleStyle: TextStyle = MaterialTheme.typography.bodyMedium,
			subtitleStyle: TextStyle = MaterialTheme.typography.bodySmall,
			subtitleFontFamily: FontFamily? = null
		): SectionRowTypography {
			return SectionRowTypography(
				titleStyle,
				subtitleStyle,
				subtitleFontFamily
			)
		}
	}
}
