package com.pissartel.entity

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import android.net.Uri

data class MapItemEntity(
    val userAvatarUrl: Uri,
    val userName: String,
    val pictureUrl: Uri,
    val latitude: Double,
    val longitude: Double,
    val pictureDescription: String,
    val likeCount: Int
) {
    @OptIn(ExperimentalUuidApi::class)
    val id: String = Uuid.random().toString()
}