package com.nin0dev.vendroid

import android.content.ActivityNotFoundException
import android.net.Uri
import android.webkit.ConsoleMessage
import android.webkit.ConsoleMessage.MessageLevel
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.nin0dev.vendroid.Logger.d
import com.nin0dev.vendroid.Logger.e
import com.nin0dev.vendroid.Logger.i
import com.nin0dev.vendroid.Logger.w
import java.util.Locale

class VChromeClient(private val activity: MainActivity) : WebChromeClient() {
    
    // Handle console messages from JavaScript
    override fun onConsoleMessage(msg: ConsoleMessage): Boolean {
        val message = String.format(Locale.ENGLISH, "[Javascript] %s @ %d: %s", msg.message(), msg.lineNumber(), msg.sourceId())
        
        // Log messages based on severity level
        when (msg.messageLevel()) {
            MessageLevel.DEBUG -> d(message)
            MessageLevel.ERROR -> e(message)
            MessageLevel.WARNING -> w(message)
            else -> i(message)
        }
        return true
    }

    // Handle file chooser requests for file selection
    override fun onShowFileChooser(webView: WebView, filePathCallback: ValueCallback<Array<Uri>>, fileChooserParams: FileChooserParams): Boolean {
        // Clear any existing file path callback
        activity.filePathCallback?.onReceiveValue(null)
        
        // Set the new callback
        activity.filePathCallback = filePathCallback
        
        // Create the file chooser intent
        val intent = fileChooserParams.createIntent()
        try {
            // Start the file chooser activity
            activity.startActivityForResult(intent, MainActivity.FILECHOOSER_RESULTCODE)
        } catch (ex: ActivityNotFoundException) {
            // Handle the case where no activity can handle the file chooser intent
            activity.filePathCallback = null
            return false
        }
        return true
    }
}
