package paige.vendroidnext.ui.screens

import android.content.Intent
import android.view.SoundEffectConstants
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import paige.vendroidnext.OptionsActivity
import paige.vendroidnext.ui.theme.VendroidTheme
import paige.vendroidnext.ui.theme.mapleMono

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OptionsScreen() {
	val view = LocalView.current
	val context = LocalContext.current
	Scaffold(
		topBar = {
			LargeTopAppBar(
				title = { Text("Options") },
				navigationIcon = {
					IconButton(
						onClick = {
							view.playSoundEffect(SoundEffectConstants.NAVIGATION_LEFT)
							(context as? OptionsActivity)?.onBackPressedDispatcher?.onBackPressed()
						},
						shape = CircleShape,
						colors = IconButtonDefaults.iconButtonColors(
							containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
							contentColor = MaterialTheme.colorScheme.onSurfaceVariant
						),
						modifier = Modifier
							.padding(start = 12.dp)
					) {
						Icon(
							imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
							contentDescription = null
						)
					}
				},
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = MaterialTheme.colorScheme.surfaceContainer,
				)
			)
		},
		containerColor = MaterialTheme.colorScheme.surfaceContainer,
		modifier = Modifier
			.fillMaxSize()
	) {
		Column(
			modifier = Modifier
				.padding(it)
				.padding(16.dp)
				.fillMaxSize(),
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			Section("General") {
				OptionRow(
					title = "Mods",
					subtitle = "Manage client mods",
					icon = Icons.Rounded.Star,
					iconBackgroundColor = MaterialTheme.colorScheme.tertiary,
					iconForegroundColor = MaterialTheme.colorScheme.onTertiary
				)
				OptionRow(
					title = "Vendroid Settings",
					subtitle = "Change Vendroid features",
					icon = Icons.Rounded.Settings,
					iconBackgroundColor = MaterialTheme.colorScheme.primary,
					iconForegroundColor = MaterialTheme.colorScheme.onPrimary
				)
			}
			Section("Info") {
				OptionRow(
					title = "About",
					subtitle = "not so stable 1.0.0",
					icon = Icons.Rounded.Info
				)
				OptionRow(
					title = "Source Code",
					subtitle = "nin0-dev/VendroidEnhanced",
					subtitleFontFamily = mapleMono,
					icon = Icons.Rounded.Code,
					onClick = {
						context.startActivity(
							Intent()
								.setAction(Intent.ACTION_VIEW)
								.setData("https://github.com/nin0-dev/VendroidEnhanced".toUri())
						)
					}
				)
			}
		}
	}
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Section(
	title: String? = null,
	content: @Composable () -> Unit
) {
	Column {
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

@Composable
fun OptionRow(
	modifier: Modifier = Modifier,

	onClick: (() -> Unit)? = null,

	title: String,
	titleStyle: TextStyle = MaterialTheme.typography.titleMedium,

	subtitle: String? = null,
	subtitleStyle: TextStyle = MaterialTheme.typography.bodySmall,
	subtitleFontFamily: FontFamily? = null,

	icon: ImageVector?,
	iconBackgroundColor: Color = MaterialTheme.colorScheme.secondary,
	iconForegroundColor: Color = MaterialTheme.colorScheme.onSecondary
) {
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
			modifier = Modifier.padding(16.dp),
			horizontalArrangement = Arrangement.spacedBy(12.dp)
		) {
			icon?.let {
				Box(
					modifier = Modifier
						.size(38.dp)
						.background(iconBackgroundColor, shape = CircleShape),
					contentAlignment = Alignment.Center
				) {
					Icon(
						imageVector = icon,
						contentDescription = null,
						tint = iconForegroundColor
					)
				}
			}
			Column {
				Text(
					title,
					style = titleStyle,
					color = MaterialTheme.colorScheme.onSurface
				)
				subtitle?.let {
					Text(
						text = subtitle,
						style = subtitleStyle,
						fontFamily = subtitleFontFamily,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
			}
		}
	}
}

@Composable
@Preview(
	showBackground = true,
	showSystemUi = true
)
private fun Preview() {
	VendroidTheme(true) {
		OptionsScreen()
	}
}
