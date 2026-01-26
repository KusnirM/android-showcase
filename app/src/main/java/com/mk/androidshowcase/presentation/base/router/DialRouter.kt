package com.mk.androidshowcase.presentation.base.router

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

interface DialRouter {

    fun dial(number: String)

    class Impl(private val context: Context) : DialRouter {
         override fun dial(number: String) {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = "tel:$number".toUri()
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    }
}


