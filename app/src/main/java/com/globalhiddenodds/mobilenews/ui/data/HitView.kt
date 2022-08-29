package com.globalhiddenodds.mobilenews.ui.data

import com.globalhiddenodds.mobilenews.ui.configuration.Utils

data class HitView(
    val objectId: String,
    val storyTitle: String?,
    val author: String?,
    val storyUrl: String?,
    val created: String?,
    var lapse: String = ""
)

fun HitView.elapse() {
    lapse = Utils.diffDate(created)
}