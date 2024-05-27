package com.shijen.a4o

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

fun shareIntent(extraString: String, context: Context) {
    if (extraString.isEmpty()) return
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, extraString)
    }
    val intent = Intent.createChooser(sendIntent, null)
    ContextCompat.startActivity(context, intent, null)
}