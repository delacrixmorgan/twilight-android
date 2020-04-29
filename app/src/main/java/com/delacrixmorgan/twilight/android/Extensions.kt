package com.delacrixmorgan.twilight.android

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.Instant
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*

fun Fragment.hideKeyboard() {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Fragment.launchPlayStore(packageName: String) {
    val url = "https://play.google.com/store/apps/details?id=$packageName"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
}

fun Fragment.launchWebsite(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)

    intent.data = Uri.parse(url)
    startActivity(intent)
}

fun Date.toZonedDateTime(): ZonedDateTime {
    val calendar = Calendar.getInstance().apply { time = this@toZonedDateTime }
    return DateTimeUtils.toZonedDateTime(calendar)
}

fun Date.toStringFormat(): String {
    val calendar = Calendar.getInstance().apply { time = this@toStringFormat }
    val zonedDateTime = DateTimeUtils.toZonedDateTime(calendar)

    return DateTimeFormatter.ISO_INSTANT.format(zonedDateTime)
}

fun Date.formatDate(format: String = "EEE, dd MMMM"): String {
    return SimpleDateFormat(format, Locale.US).format(this)
}

fun String.toDateFormat(): Date {
    val dateInstant = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(this))
    return DateTimeUtils.toDate(dateInstant)
}

fun String.extractKeywords(): List<String> {
    val splitString = this.split("/")
    val list = mutableListOf<String>()

    splitString.forEach {
        val name = it.replace("_", " ")
        name.addExceptions(list)
        list.add(name)
    }

    return list
}

fun String.addExceptions(list: MutableList<String>) {
    val exceptionList = when (this) {
        "Kuala Lumpur" -> {
            listOf("Malaysia", "Klang", "Shah Alam", "Rawang", "Subang Jaya")
        }
        "Ho Chi Minh" -> {
            listOf("Hanoi", "Vietnam", "Saigon")
        }
        "Los Angeles" -> {
            listOf("San Francisco")
        }
        "Chicago" -> {
            listOf("Columbus", "Ohio")
        }
        "New York" -> {
            listOf("New Jersey")
        }
        "Bucharest" -> {
            listOf("Romania", "Transylvania")
        }
        "Tokyo" -> {
            listOf("Japan", "Kyoto", "Osaka", "Edo", "Kobe")
        }
        else -> listOf()
    }

    list.addAll(exceptionList)
}

fun Fragment.performHapticContextClick() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        view?.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
    } else {
        view?.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
    }
}

fun ZonedDateTime.getZoneCity(): String {
    val splitString = this.zone.toString().split("/")
    return splitString.last().replace("_", " ")
}