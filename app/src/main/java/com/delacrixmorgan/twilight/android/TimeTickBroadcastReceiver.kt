package com.delacrixmorgan.twilight.android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TimeTickBroadcastReceiver(private val listener: TimeTickListener) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action?.compareTo(Intent.ACTION_TIME_TICK) == 0) {
            listener.onTimeTicked()
        }
    }
}