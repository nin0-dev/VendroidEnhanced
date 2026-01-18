package com.nin0dev.vendroid.utils

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.nin0dev.vendroid.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

object VencordManager {
	var vencord = ""
	var vencordVersion: String? = null

	fun getVencordContents(context: Context, callback: () -> Unit) {
		DS(context).getNRString("clientMod", "VENCORD") { mod ->
			DS(context).getNRInt("lastMajorVerUpdateWithVencord", 0) { ver ->
				val file = File(context.filesDir, mod)
				if (file.exists() && ver < BuildConfig.VERSION_CODE) {
					vencord = file.readText()
				}
				else {
					val queue = Volley.newRequestQueue(context)
					val stringRequest = StringRequest(
						Request.Method.GET,
						BUNDLE_PER_CLIENT_MOD[mod],
						{ response ->
							vencord = response
							file.writeText(response)
							DS(context).setInt("lastMajorVerUpdateWithVencord", BuildConfig.VERSION_CODE)
							callback()
						},
						{ error ->
							if (BuildConfig.DEBUG) {
								Log.e("Network error while fetching Vencord", error.toString())
								callback()
							}
						}
					)
					stringRequest.setShouldCache(false);
					queue.add(stringRequest)
				}
			}
		}

	}
}
