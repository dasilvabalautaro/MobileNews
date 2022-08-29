package com.globalhiddenodds.mobilenews.ui.data

data class HitView(
    val objectId: String,
    val storyTitle: String?,
    val author: String?,
    val storyUrl: String?,
    val created: Double?,
    var lapse: String = ""
)