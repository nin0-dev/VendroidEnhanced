package com.nin0dev.vendroid.utils

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.nin0dev.vendroid.BuildConfig
import com.nin0dev.vendroid.utils.VencordManager.vencord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Serializable
data class UpdateData(
	val update: Announcement?,
	val announcements: List<Announcement>?
)

@Serializable
data class Announcement(
	val id: Int,
	val title: String,
	val text: String
)

fun getUpdates(context: Context, callback: (UpdateData) -> Unit) {
	DS(context).getNRInt("lastDay", 0) { lastDay ->
		val now = ChronoUnit.DAYS.between(
			LocalDate.of(1970, 1, 1),
			LocalDate.now()
		)
		val queue = Volley.newRequestQueue(context)
		val stringRequest = StringRequest(
			Request.Method.GET,
			"$UPDATE_API/api/updates?version=${BuildConfig.VERSION_CODE}${if (now > lastDay) "&daily=true" else ""}",
			{ response ->
				val data = Json.decodeFromString<UpdateData>(response)
				DS(context).setInt("lastDay", now.toInt())
				callback(data)
			},
			{ error ->
				throw error
			}
		)
		stringRequest.setShouldCache(false);
		queue.add(stringRequest)
	}
}
