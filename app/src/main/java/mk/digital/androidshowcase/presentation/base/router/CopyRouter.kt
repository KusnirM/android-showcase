package mk.digital.androidshowcase.presentation.base.router

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

interface CopyRouter {
    fun copyToClipboard(text: String)

    class Impl(private val context: Context) : CopyRouter {
        override fun copyToClipboard(text: String) {
            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", text)
            clipboardManager.setPrimaryClip(clip)
        }
    }
}


