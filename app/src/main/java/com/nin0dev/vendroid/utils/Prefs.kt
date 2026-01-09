package com.nin0dev.vendroid.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

val Context.DataStore by preferencesDataStore("settings")

class DS(private val context: Context) {
	@Composable
	fun getString(key: String, default: String): State<String> {
		return context.DataStore.data
			.map { it[stringPreferencesKey(key)] ?: default}
			.collectAsState(initial = default)
	}

	fun getNRString(key: String, default: String, callback: (String) -> Unit) {
		CoroutineScope(Dispatchers.IO).launch {
			val value = context.DataStore.data
				.map { it[stringPreferencesKey(key)] ?: default }
				.first()

			withContext(Dispatchers.Main) {
				callback(value)
			}
		}
	}

	@Composable
	fun getBool(key: String, default: Boolean): State<Boolean> {
		return context.DataStore.data
			.map { it[booleanPreferencesKey(key)] ?: default}
			.collectAsState(initial = default)
	}

	fun getNRBool(
		key: String,
		default: Boolean,
		callback: (Boolean) -> Unit
	) {
		CoroutineScope(Dispatchers.IO).launch {
			val value = context.DataStore.data
				.map { it[booleanPreferencesKey(key)] ?: default }
				.first()

			withContext(Dispatchers.Main) {
				callback(value)
			}
		}
	}
	@Composable
	fun getInt(key: String, default: Int): State<Int> {
		return context.DataStore.data
			.map { it[intPreferencesKey(key)] ?: default}
			.collectAsState(initial = default)
	}

	fun getNRInt(
		key: String,
		default: Int,
		callback: (Int) -> Unit
	) {
		CoroutineScope(Dispatchers.IO).launch {
			val value = context.DataStore.data
				.map { it[intPreferencesKey(key)] ?: default }
				.first()

			withContext(Dispatchers.Main) {
				callback(value)
			}
		}
	}

	fun setString(scope: CoroutineScope, key: String, value: String) {
		scope.launch {
			context.DataStore.edit { it[stringPreferencesKey(key)] = value }
		}
	}

	fun setBool(scope: CoroutineScope, key: String, value: Boolean) {
		scope.launch {
			context.DataStore.edit { it[booleanPreferencesKey(key)] = value }
		}
	}

	fun setInt(scope: CoroutineScope, key: String, value: Int) {
		scope.launch {
			context.DataStore.edit { it[intPreferencesKey(key)] = value }
		}
	}
}
