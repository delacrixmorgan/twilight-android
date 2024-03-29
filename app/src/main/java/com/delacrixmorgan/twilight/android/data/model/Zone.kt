package com.delacrixmorgan.twilight.android.data.model

import java.util.*

data class Zone(
    val uuid: String = UUID.randomUUID().toString(),
    val timeZoneId: String,
    val name: String,
    val keywords: List<String>
) {
    val regionZoneName: String
        get() = "${keywords[0]}/${keywords[keywords.size - 1]}"
}