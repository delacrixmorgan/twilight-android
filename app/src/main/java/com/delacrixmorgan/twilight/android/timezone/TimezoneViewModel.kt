package com.delacrixmorgan.twilight.android.timezone

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@HiltViewModel
class TimezoneViewModel : ViewModel() {
    fun m1() {
        // Get the current time in Amsterdam
        val amsterdamTime = Calendar.getInstance(TimeZone.getTimeZone("Europe/Amsterdam")).time

        // Convert Amsterdam time to Malaysia time
        val malaysiaTime = convertToMalaysiaTime(amsterdamTime)

        // Format the times for display
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val amsterdamTimeString = formatter.format(amsterdamTime)
        val malaysiaTimeString = formatter.format(malaysiaTime)

        // Display the results
        println("Amsterdam Time: $amsterdamTimeString")
        println("Malaysia Time: $malaysiaTimeString")
    }

    private fun convertToMalaysiaTime(amsterdamTime: Date): Date {
        // Convert Amsterdam time to Malaysia time
        val amsterdamTimeZone = TimeZone.getTimeZone("Europe/Amsterdam")
        val malaysiaTimeZone = TimeZone.getTimeZone("Asia/Kuala_Lumpur")

        val amsterdamOffset = amsterdamTimeZone.rawOffset.toLong()
        val malaysiaOffset = malaysiaTimeZone.rawOffset.toLong()

        val timeDifference = malaysiaOffset - amsterdamOffset

        return Date(amsterdamTime.time + timeDifference)
    }
}