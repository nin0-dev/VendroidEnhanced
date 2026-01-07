package com.nin0dev.vendroid.ui.components

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.nin0dev.vendroid.BuildConfig


object VencordManager {
	var vencord = ""

	fun getVencordContents(context: Context, callback: () -> Unit) {
		val queue = Volley.newRequestQueue(context)
		val stringRequest = StringRequest(
			Request.Method.GET, "https://github.com/VendroidEnhanced/plugin/releases/download/vencord/browser.js",
			{ response ->
				vencord = response
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
