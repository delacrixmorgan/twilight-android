package com.delacrixmorgan.twilight.android.data.controller

import android.content.Context
import androidx.core.content.ContextCompat
import com.delacrixmorgan.twilight.android.R
import org.threeten.bp.ZonedDateTime

object DateTimeController {

    fun getStatus(dateTime: ZonedDateTime): String {
        return if (dateTime.hour < 7 || dateTime.hour > 19) {
            "🌙"
        } else if (dateTime.hour == 7 || dateTime.hour == 17 || dateTime.hour == 18) {
            "🌤"
        } else {
            "☀"
        }
    }

    fun getGreetingText(context: Context, dateTime: ZonedDateTime): String {
        return when (dateTime.hour) {
            in 0..11 -> {
                context.getString(R.string.good_morning)
            }
            in 12..17 -> {
                context.getString(R.string.good_afternoon)
            }
            in 18..21 -> {
                context.getString(R.string.good_evening)
            }
            else -> {
                context.getString(R.string.good_night)
            }
        }
    }

    fun getBackgroundColorTint(context: Context, dateTime: ZonedDateTime): Int {
        return when (dateTime.hour) {
            in 6..7 -> {
                ContextCompat.getColor(context, R.color.colorTintDawn)
            }
            in 8..16 -> {
                ContextCompat.getColor(context, R.color.colorTintDay)
            }
            in 17..18 -> {
                ContextCompat.getColor(context, R.color.colorTintDawn)
            }
            else -> {
                ContextCompat.getColor(context, R.color.colorTintNight)
            }
        }
    }

    fun getTextColorTint(context: Context, dateTime: ZonedDateTime): Int {
        return when (dateTime.hour) {
            in 6..7 -> {
                ContextCompat.getColor(context, android.R.color.black)
            }
            in 8..16 -> {
                ContextCompat.getColor(context, android.R.color.white)
            }
            in 17..18 -> {
                ContextCompat.getColor(context, android.R.color.black)
            }
            else -> {
                ContextCompat.getColor(context, android.R.color.white)
            }
        }
    }
}