package com.sangkon.ch10

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.RemoteInput

class ReplyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val replyTxt = RemoteInput.getResultsFromIntent(intent)?.getCharSequence("key_text_reply")
        Log.d("deu", "replyTxt:$replyTxt")

        val manager = context.getSystemService(
            AppCompatActivity.NOTIFICATION_SERVICE
        ) as NotificationManager

        manager.cancel(11)
    }
}