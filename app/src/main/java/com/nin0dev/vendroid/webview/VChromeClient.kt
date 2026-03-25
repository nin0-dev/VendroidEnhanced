package com.nin0dev.vendroid.webview

import android.content.ActivityNotFoundException
import android.net.Uri
import android.webkit.ConsoleMessage
import android.webkit.ConsoleMessage.MessageLevel
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.nin0dev.vendroid.MainActivity
import com.nin0dev.vendroid.utils.Logger.d
import com.nin0dev.vendroid.utils.Logger.e
import com.nin0dev.vendroid.utils.Logger.i
import com.nin0dev.vendroid.utils.Logger.w
import java.util.Locale

class VChromeClient(private val activity: MainActivity) : WebChromeClient() {
    override fun onConsoleMessage(msg: ConsoleMessage): Boolean {
        val m = String.format(Locale.ENGLISH, "[Javascript] %s @ %d: %s", msg.message(), msg.lineNumber(), msg.sourceId())
        when (msg.messageLevel()) {
            MessageLevel.DEBUG -> d(m)
            MessageLevel.ERROR -> e(m)
            MessageLevel.WARNING -> w(m)
            else -> i(m)
        }
        return true
    }

    override fun onShowFileChooser(
        webView: WebView,
        filePathCallback: ValueCallback<Array<Uri>>,
        fileChooserParams: FileChooserParams
    ): Boolean {
        activity.filePathCallback?.onReceiveValue(null)
        activity.filePathCallback = null

        activity.filePathCallback = filePathCallback

        return try {
            val intent = fileChooserParams.createIntent()
            activity.startActivityForResult(intent, MainActivity.FILECHOOSER_RESULTCODE)
            true
        } catch (e: ActivityNotFoundException) {
            activity.filePathCallback = null
            false
        }
    }
}
