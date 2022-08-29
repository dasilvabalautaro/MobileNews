package com.globalhiddenodds.mobilenews.ui.data

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

data class HitView(
    val objectId: String,
    val storyTitle: String?,
    val author: String?,
    val storyUrl: String?,
    val created: String?,
    var lapse: String = ""
)

fun HitView.elapse() {
    if (created != null && created.length >= 19) {
        val splitCreate = created.substring(0, 19)
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val textNow = LocalDateTime.now().format(formatter)
        val timeNow = LocalDateTime.parse(textNow, formatter)
        val timeCreate = LocalDateTime.parse(splitCreate, formatter)
        val millNow = timeNow.toInstant(ZoneOffset.UTC).toEpochMilli()
        val millCreate = timeCreate.toInstant(ZoneOffset.UTC).toEpochMilli()
        val diff = millNow - millCreate
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        val days = TimeUnit.MILLISECONDS.toDays(diff)

        lapse = when {
            days > 1 -> days.toString() + "days"
            hours in 1..23 -> hours.toString() + "h"
            days == 1L -> "Yesterday"
            minutes < 60 -> minutes.toString() + "m"
            else -> ""
        }
    }
}