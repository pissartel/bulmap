package com.pissartel.domain.usecase

import com.pissartel.entity.MapItemEntity
import kotlinx.coroutines.flow.Flow

fun interface GetCloseMapPicturesUseCase {
    suspend operator fun invoke(latitude: Double, longitude: Double): Flow<List<MapItemEntity>>
}