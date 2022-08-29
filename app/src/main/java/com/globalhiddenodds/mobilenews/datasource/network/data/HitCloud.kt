package com.globalhiddenodds.mobilenews.datasource.network.data

import com.globalhiddenodds.mobilenews.datasource.database.data.Hit

data class HitCloud(
    val objectId: String,
    val parentId: Double?,
    val storyId: Double?,
    val storyTitle: String?,
    val author: String?,
    val storyUrl: String?,
    val created: String?,
    val createdI: Double?
)

fun HitCloud.toHit(): Hit = Hit(
    objectId,
    parentId,
    storyId,
    storyTitle,
    author,
    storyUrl,
    created,
    createdI)