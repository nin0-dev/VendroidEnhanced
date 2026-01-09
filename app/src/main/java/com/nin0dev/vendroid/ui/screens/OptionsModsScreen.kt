package com.nin0dev.vendroid.ui.screens

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nin0dev.vendroid.ui.components.forms.Form
import com.nin0dev.vendroid.ui.components.forms.FormScaffold
import com.nin0dev.vendroid.ui.components.forms.Section
import com.nin0dev.vendroid.ui.components.forms.SectionRow
import com.nin0dev.vendroid.ui.components.forms.SectionRowLabel
import com.nin0dev.vendroid.ui.theme.VendroidTheme
import com.nin0dev.vendroid.utils.DS
import com.nin0dev.vendroid.utils.DataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

enum class VencordSource(val displayName: String, val description: String? = null) {
	VENCORD("Vencord", "The official version of Vencord, with some extra mobile-friendly tweaks"),
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
	val scope = rememberCoroutineScope()
	val vencordSources = VencordSource.entries
	val ds = DS(LocalContext.current)
	val clientMod = ds.getString("clientMod", "VENCORD")
	val canUseDevbuild = ds.getBool("canUseDevbuild", false)

	FormScaffold("Manage mods", onBackPressed) { it ->
		Form(it) {
			Section("Client mods") {
				SectionRow(horizontalArrangement = Arrangement.SpaceBetween) {
					Column(
						modifier = Modifier.selectableGroup(),
						verticalArrangement = Arrangement.spacedBy(10.dp)
					) {
						vencordSources.filter { if (canUseDevbuild.value) true else it.name != "CUSTOM" }.forEach { source ->
							Row(
								Modifier
									.fillMaxWidth()
									.selectable(
										selected = source.name == clientMod.value,
										onClick = { scope.launch {
											ds.setString(scope, "clientMod", source.name)
										} },
										role = Role.RadioButton
									),
								verticalAlignment = Alignment.Top
							) {
								RadioButton(
									selected = (source.name == clientMod.value),
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
											enabled = source.name == clientMod.value
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

			Section(title = "Developer only") {
				SectionRow(horizontalArrangement = Arrangement.SpaceBetween) {
					SectionRowLabel(title = "Use a custom Vencord location")
					Switch(
						checked = canUseDevbuild.value,
						onCheckedChange = {
							ds.setBool(scope, "canUseDevbuild", it)
							if (clientMod.value == "CUSTOM") ds.setString(scope, "clientMod", "VENCORD")
						}
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
