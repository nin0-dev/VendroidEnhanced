package com.nin0dev.vendroid

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale

object HttpClient {
    @JvmField
    var VencordRuntime: String? = null
    @JvmField
    var VencordMobileRuntime: String? = null

    @JvmStatic
    @Throws(IOException::class)
    fun fetchVencord(activity: Activity) {
        val sPrefs = activity.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val e = sPrefs.edit()
        val bundleURLToUse = if(sPrefs.getBoolean("equicord", false)) Constants.EQUICORD_BUNDLE_URL else Constants.JS_BUNDLE_URL
        val vendroidFile = File(activity.filesDir, "vencord.js")
        val res = activity.resources
        res.openRawResource(R.raw.vencord_mobile).use { `is` -> VencordMobileRuntime = readAsText(`is`) }

        // If VencordRuntime is already initialized, no need to fetch again
        if (VencordRuntime != null) return

        // Check for app version updates or force redownload for debugging
        if (sPrefs.getInt("lastMajorUpdateThatUserHasUpdatedVencord", 0) < BuildConfig.VERSION_CODE) {
            if (BuildConfig.DEBUG) {
                Toast.makeText(activity, "Just updated app version, redownloading Vencord", Toast.LENGTH_LONG).show()
            }
            vendroidFile.delete()
        }
        if ((sPrefs.getString("vencordLocation", Constants.JS_BUNDLE_URL) != Constants.JS_BUNDLE_URL && 
            sPrefs.getString("vencordLocation", Constants.JS_BUNDLE_URL) != Constants.EQUICORD_BUNDLE_URL) || BuildConfig.DEBUG) {
            // Redownload Vencord if debugging
            Toast.makeText(activity, "Debugging app or Vencord, bundle will be redownloaded. Avoid using on limited networks", Toast.LENGTH_LONG).show()
            vendroidFile.delete()
        }

        // If Vencord file exists, use it, else download and save
        if (vendroidFile.exists()) {
            VencordRuntime = vendroidFile.readText()
        } else {
            val conn = fetch(sPrefs.getString("vencordLocation", bundleURLToUse)!!)
            vendroidFile.writeText(readAsText(conn.inputStream))
            e.putInt("lastMajorUpdateThatUserHasUpdatedVencord", BuildConfig.VERSION_CODE)
            e.apply()
            VencordRuntime = vendroidFile.readText()
        }
    }

    @Throws(IOException::class)
    fun fetch(url: String): HttpURLConnection {
        val conn = URL(url).openConnection() as HttpURLConnection
        if (conn.getResponseCode() >= 300) {
            throw HttpException(conn)
        }
        return conn
    }

    @Throws(IOException::class)
    fun readAsText(`is`: InputStream): String {
        ByteArrayOutputStream().use { baos ->
            val buf = ByteArray(16384) // 16 KB buffer
            var n: Int
            while (`is`.read(buf).also { n = it } > -1) {
                baos.write(buf, 0, n)
            }
            baos.flush()
            return baos.toString("UTF-8")
        }
    }

    class HttpException(private val conn: HttpURLConnection) : IOException() {
        override var message: String? = null
            get() {
                if (field == null) {
                    try {
                        conn.errorStream.use { es ->
                            field = String.format(
                                    Locale.ENGLISH,
                                    "%d: %s (%s)\n%s",
                                    conn.getResponseCode(),
                                    conn.getResponseMessage(),
                                    conn.url.toString(),
                                    readAsText(es)
                            )
                        }
                    } catch (ex: IOException) {
                        field = "Error while building message lmao. Url is " + conn.url.toString()
                    }
                }
                return field
            }
            private set
    }
}
