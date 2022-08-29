package com.globalhiddenodds.mobilenews.datasource.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.globalhiddenodds.mobilenews.ui.data.HitView
import com.globalhiddenodds.mobilenews.workers.ConstantsHit

@Entity(tableName = "hit")
data class Hit(
    @PrimaryKey @ColumnInfo(name = ConstantsHit.KEY_OBJECT_ID)
    val objectId: String,
    @ColumnInfo(name = ConstantsHit.KEY_PARENT_ID, defaultValue = "0")
    val parentId: Double?,
    @ColumnInfo(name = ConstantsHit.KEY_STORY_ID, defaultValue = "0")
    val storyId: Double?,
    @ColumnInfo(name = ConstantsHit.KEY_TITLE, defaultValue = "")
    val storyTitle: String?,
    @ColumnInfo(name = ConstantsHit.KEY_AUTHOR, defaultValue = "")
    val author: String?,
    @ColumnInfo(name = ConstantsHit.KEY_STORY_URL, defaultValue = "")
    val storyUrl: String?,
    @ColumnInfo(name = ConstantsHit.KEY_CREATED, defaultValue = "")
    val created: String?,
    @ColumnInfo(name = ConstantsHit.KEY_CREATED_I, defaultValue = "0")
    val createdI: Double?,
    @ColumnInfo(name = ConstantsHit.KEY_STATE, defaultValue = "1")
    val state: Int = 1)

fun Hit.toHitView(): HitView = HitView(
    objectId,
    storyTitle,
    author,
    storyUrl,
    created)