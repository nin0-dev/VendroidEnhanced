package dev.nin0.vendroid.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.nin0.vendroid.ui.components.forms.Form
import dev.nin0.vendroid.ui.components.forms.FormScaffold
import dev.nin0.vendroid.ui.components.forms.Section
import dev.nin0.vendroid.ui.components.forms.SectionRow
import dev.nin0.vendroid.ui.components.forms.SectionRowLabel
import dev.nin0.vendroid.ui.theme.VendroidTheme

enum class VencordSource(val displayName: String, val description: String? = null) {
	OFFICIAL("Official", "The official version of Vencord"),
	EQUICORD(
		"Equicord",
		"Fork of Vencord with extra plugins. Does not have the same quality standards as Vencord and may have risky plugins"
	),
	CUSTOM("Custom")
}

@Composable
fun OptionsModsScreen(
	onBackPressed: () -> Unit = {}
) {
	val vencordSources = VencordSource.entries
	val (selectedSource, onSourceSelected) = remember { mutableStateOf(vencordSources[0]) }

	FormScaffold("Manage mods", onBackPressed) {
		Form(it) {
			Text(
				"Manage and customize client mods. Multiple mods may be used at once. Not all mods will have the same level of support as Vencord and are being provided as-is",
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurfaceVariant,
				modifier = Modifier.padding(20.dp)
			)
			Section("Vencord") {
				SectionRow(horizontalArrangement = Arrangement.SpaceBetween) {
					SectionRowLabel(title = "Use Vencord")
					Switch(
						checked = true,
						onCheckedChange = {}
					)
				}
				SectionRow(horizontalArrangement = Arrangement.SpaceBetween) {
					Column(
						modifier = Modifier.selectableGroup(),
						verticalArrangement = Arrangement.spacedBy(10.dp)
					) {
						vencordSources.forEach { source ->
							Row(
								Modifier
									.fillMaxWidth()
									.selectable(
										selected = source == selectedSource,
										onClick = { onSourceSelected(source) },
										role = Role.RadioButton
									),
								verticalAlignment = Alignment.Top
							) {
								RadioButton(
									selected = (source == selectedSource),
									onClick = null
								)
								Column {
									Text(
										text = source.displayName,
										style = MaterialTheme.typography.bodyLarge,
										modifier = Modifier.padding(start = 16.dp)
									)
									when (source) {
										VencordSource.CUSTOM -> OutlinedTextField(
											state = rememberTextFieldState(),
											label = { Text("URL") },
											enabled = source == selectedSource
										)
										else -> {
											source.description?.let {
												Text(
													text = source.description,
													style = MaterialTheme.typography.bodyMedium,
													color = MaterialTheme.colorScheme.onSurfaceVariant,
													modifier = Modifier.padding(start = 16.dp)
												)
											}
										}
									}
								}
							}
						}
					}
				}
			}
			Section("Shelter") {
				SectionRow(horizontalArrangement = Arrangement.SpaceBetween) {
					SectionRowLabel(title = "Use Shelter")
					Switch(
						checked = false,
						onCheckedChange = {}
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
		OptionsModsScreen()
	}
}
