package com.delacrixmorgan.twilight.android.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.delacrixmorgan.twilight.android.service.TimeTickListener

class TimeTickBroadcastReceiver(private val listener: TimeTickListener) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action?.compareTo(Intent.ACTION_TIME_TICK) == 0) {
            listener.onTimeTicked()
        }
    }
}