package com.pissartel.domain.usecase

import com.pissartel.entity.MapItemEntity
import kotlinx.coroutines.flow.Flow

fun interface GetMapPictureUseCase {
    suspend operator fun invoke(id: String): Flow<MapItemEntity?>
}