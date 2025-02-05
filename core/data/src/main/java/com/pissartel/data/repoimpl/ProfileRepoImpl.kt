package com.pissartel.data.repoimpl

import android.net.Uri
import com.pissartel.data.utils.CacheResource
import com.pissartel.domain.repository.ProfileRepository
import com.pissartel.entity.MapItemEntity
import com.pissartel.entity.ProfileEntity
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ProfileRepoImpl @Inject constructor() : ProfileRepository {

    private val profile = CacheResource<ProfileEntity>()
    private val likes = CacheResource<List<String>>()
    private val mapPictureItems = CacheResource<List<MapItemEntity>>()

    override suspend fun getProfile(): StateFlow<ProfileEntity?> {
        profile.fetchResource(
            fetchFromRemote = { ProfileEntity() }
        )
        return profile.data
    }

    override suspend fun addLike(id: String) {
        val currentLikes = likes.data.value?.toMutableList() ?: emptyList<String>().toMutableList()
        if (!currentLikes.contains(id)) {
            currentLikes.add(id)
            likes.update(currentLikes)
        }
    }

    override suspend fun removeLike(id: String) {
        val currentLikes = likes.data.value?.toMutableList() ?: emptyList<String>().toMutableList()
        if (currentLikes.contains(id)) {
            currentLikes.remove(id)
            likes.update(currentLikes)
        }
    }

    override suspend fun getLikes(): StateFlow<List<String>?> {
        return likes.data
    }

    override suspend fun getMapPictureItems(): StateFlow<List<MapItemEntity>?> {
        return mapPictureItems.data
    }

    override suspend fun createMapPictureItem(
        pictureUrl: Uri,
        latitude: Double,
        longitude: Double,
        pictureDescription: String
    ) {
        val newMapPicture = MapItemEntity(
            userAvatarUrl = profile.data.value?.userAvatar ?: Uri.EMPTY,
            userName = profile.data.value?.userName ?: "",
            pictureUrl = pictureUrl,
            latitude = latitude,
            longitude = longitude,
            pictureDescription = pictureDescription,
            likeCount = 0
        )

        val mapItemEntities = mapPictureItems.data.value?.toMutableList()
            ?: emptyList<MapItemEntity>().toMutableList()
        mapItemEntities.add(newMapPicture)
        mapPictureItems.update(mapItemEntities)
    }
}