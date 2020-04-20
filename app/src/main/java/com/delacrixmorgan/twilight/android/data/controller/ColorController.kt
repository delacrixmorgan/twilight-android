package com.delacrixmorgan.twilight.android.data.controller

import android.content.Context
import androidx.core.content.ContextCompat
import com.delacrixmorgan.twilight.android.R
import org.threeten.bp.ZonedDateTime

object ColorController {
    fun getBackgroundColorTint(context: Context, dateTime: ZonedDateTime): Int {
        val hour = dateTime.hour
        return when {
            hour <= 7 -> {
                ContextCompat.getColor(context, R.color.colorTint1)
            }
            hour == 8 -> {
                ContextCompat.getColor(context, R.color.colorTint2)
            }
            hour == 9 -> {
                ContextCompat.getColor(context, R.color.colorTint3)
            }
            hour == 10 -> {
                ContextCompat.getColor(context, R.color.colorTint4)
            }
            hour == 11 -> {
                ContextCompat.getColor(context, R.color.colorTint5)
            }
            hour <= 13 -> {
                ContextCompat.getColor(context, R.color.colorTint6)
            }
            hour <= 15 -> {
                ContextCompat.getColor(context, R.color.colorTint5)
            }
            hour == 16 -> {
                ContextCompat.getColor(context, R.color.colorTint4)
            }
            hour == 17 -> {
                ContextCompat.getColor(context, R.color.colorTint3)
            }
            hour == 18 -> {
                ContextCompat.getColor(context, R.color.colorTint2)
            }
            else -> {
                ContextCompat.getColor(context, R.color.colorTint1)
            }
        }
    }

    fun getTextColorTint(context: Context, dateTime: ZonedDateTime): Int {
        val hour = dateTime.hour
        return when {
            hour <= 7 -> {
                ContextCompat.getColor(context, android.R.color.white)
            }
            hour == 8 -> {
                ContextCompat.getColor(context, android.R.color.white)
            }
            hour == 9 -> {
                ContextCompat.getColor(context, android.R.color.white)
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