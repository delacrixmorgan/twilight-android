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

    fun getGreetingText(context: Context, dateTime: ZonedDateTime, name: String): String {
        return when (dateTime.hour) {
            in 0..11 -> {
                "Good Morning, $name"
            }
            in 12..17 -> {
                "Good Afternoon, $name"
            }
            in 18..21 -> {
                "Good Evening, $name"
            }
            else -> {
                "Good Night, $name"
            }
        }
    }

    fun getBackgroundColorTint(context: Context, dateTime: ZonedDateTime): Int {
        val hour = dateTime.hour
        return when {
            hour <= 6 -> {
                ContextCompat.getColor(context, R.color.colorTint1)
            }
            hour == 7 -> {
                ContextCompat.getColor(context, R.color.colorTint4)
            }
            hour == 8 -> {
                ContextCompat.getColor(context, R.color.colorTint6)
            }
            hour == 9 -> {
                ContextCompat.getColor(context, R.color.colorTint6)
            }
            hour == 10 -> {
                ContextCompat.getColor(context, R.color.colorTint6)
            }
            hour == 11 -> {
                ContextCompat.getColor(context, R.color.colorTint6)
            }
            hour <= 13 -> {
                ContextCompat.getColor(context, R.color.colorTint6)
            }
            hour <= 15 -> {
                ContextCompat.getColor(context, R.color.colorTint6)
            }
            hour == 16 -> {
                ContextCompat.getColor(context, R.color.colorTint6)
            }
            hour == 17 -> {
                ContextCompat.getColor(context, R.color.colorTint4)
            }
            hour == 18 -> {
                ContextCompat.getColor(context, R.color.colorTint4)
            }
            else -> {
                ContextCompat.getColor(context, R.color.colorTint1)
            }
        }
    }

    fun getTextColorTint(context: Context, dateTime: ZonedDateTime): Int {
        val hour = dateTime.hour
        return when {
            hour <= 6 -> {
                ContextCompat.getColor(context, android.R.color.white)
            }
            hour == 7 -> {
                ContextCompat.getColor(context, android.R.color.white)
            }
            hour == 8 -> {
                ContextCompat.getColor(context, android.R.color.black)
            }
            hour == 9 -> {
                ContextCompat.getColor(context, android.R.color.black)
            }
            hour == 10 -> {
                ContextCompat.getColor(context, android.R.color.black)
            }
            hour == 11 -> {
                ContextCompat.getColor(context, android.R.color.black)
            }
            hour <= 13 -> {
                ContextCompat.getColor(context, android.R.color.black)
            }
            hour <= 15 -> {
                ContextCompat.getColor(context, android.R.color.black)
            }
            hour == 16 -> {
                ContextCompat.getColor(context, android.R.color.black)
            }
            hour == 17 -> {
                ContextCompat.getColor(context, android.R.color.white)
            }
            hour == 18 -> {
                ContextCompat.getColor(context, android.R.color.white)
            }
            else -> {
                ContextCompat.getColor(context, android.R.color.white)
            }
        }
    }
}