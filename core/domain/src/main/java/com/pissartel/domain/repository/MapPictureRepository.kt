package com.pissartel.domain.repository

import kotlinx.coroutines.flow.Flow

interface MapPictureRepository {
    suspend fun getCloseMapPictures(latitude: Double, longitude: Double): Flow<List<com.pissartel.entity.MapItemEntity>>
    suspend fun getFeed(): Flow<List<com.pissartel.entity.MapItemEntity>>
}