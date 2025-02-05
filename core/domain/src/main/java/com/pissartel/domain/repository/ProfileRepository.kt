package com.pissartel.domain.repository

import android.net.Uri
import com.pissartel.entity.MapItemEntity
import com.pissartel.entity.ProfileEntity
import kotlinx.coroutines.flow.StateFlow

interface ProfileRepository {
    suspend fun getProfile(): StateFlow<ProfileEntity?>
    suspend fun addLike(id: String)
    suspend fun removeLike(id: String)
    suspend fun getLikes(): StateFlow<List<String>?>
    suspend fun getMapPictureItems(): StateFlow<List<MapItemEntity>?>

    // Should ve in map picture repo but for mocking purpose is here
    suspend fun createMapPictureItem(
        pictureUrl: Uri,
        latitude: Double,
        longitude: Double,
        pictureDescription: String,
    )
}