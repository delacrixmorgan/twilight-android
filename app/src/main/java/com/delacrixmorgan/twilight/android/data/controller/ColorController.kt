package com.delacrixmorgan.twilight.android.data.controller

import android.content.Context
import androidx.core.content.ContextCompat
import com.delacrixmorgan.twilight.android.R
import com.delacrixmorgan.twilight.android.data.model.ColorTintState
import org.threeten.bp.ZonedDateTime

object ColorController {

    fun getColorTintState(context: Context, dateTime: ZonedDateTime): ColorTintState {
        return when (0) {
            in 7..8 -> {
                ColorTintState(
                    colorDark = ContextCompat.getColor(context, R.color.colorTintAmberDark),
                    colorMedium = ContextCompat.getColor(context, R.color.colorTintAmberMedium),
                    colorLight = ContextCompat.getColor(context, R.color.colorTintAmberLight),
                    colorFade = ContextCompat.getColor(context, R.color.colorTintAmberFade),
                    colorHint = ContextCompat.getColor(context, R.color.colorBlack)
                )
            }
            in 9..17 -> {
                ColorTintState(
                    colorDark = ContextCompat.getColor(context, R.color.colorTintBlueDark),
                    colorMedium = ContextCompat.getColor(context, R.color.colorTintBlueMedium),
                    colorLight = ContextCompat.getColor(context, R.color.colorTintBlueLight),
                    colorFade = ContextCompat.getColor(context, R.color.colorTintBlueFade),
                    colorHint = ContextCompat.getColor(context, android.R.color.white)
                )
            }
            in 18..19 -> {
                ColorTintState(
                    colorDark = ContextCompat.getColor(context, R.color.colorTintAmberDark),
                    colorMedium = ContextCompat.getColor(context, R.color.colorTintAmberMedium),
                    colorLight = ContextCompat.getColor(context, R.color.colorTintAmberLight),
                    colorFade = ContextCompat.getColor(context, R.color.colorTintAmberFade),
                    colorHint = ContextCompat.getColor(context, R.color.colorBlack)
                )
            }
            else -> {
                ColorTintState(
                    colorDark = ContextCompat.getColor(context, R.color.colorTintGreyDark),
                    colorMedium = ContextCompat.getColor(context, R.color.colorTintGreyMedium),
                    colorLight = ContextCompat.getColor(context, R.color.colorTintGreyLight),
                    colorFade = ContextCompat.getColor(context, R.color.colorTintGreyFade),
                    colorHint = ContextCompat.getColor(context, android.R.color.black)
                )
            }
        }
    }

    fun getBackgroundColorTint(context: Context, dateTime: ZonedDateTime): Int {
        val hour = dateTime.hour
        return when {
            hour <= 6 -> {
                ContextCompat.getColor(context, R.color.colorPrimary)
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
                ContextCompat.getColor(context, R.color.colorPrimary)
            }
        }
    }

    fun getTextColorTint(context: Context, dateTime: ZonedDateTime): Int {
        val hour = dateTime.hour
        return when {
            hour <= 6 -> {
                ContextCompat.getColor(context, android.R.color.white)
            }
            hour == 7 ->{
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
                ContextCompat.getColor(context, android.R.color.black)
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