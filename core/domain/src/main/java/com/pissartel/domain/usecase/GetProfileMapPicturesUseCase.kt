package com.pissartel.domain.usecase

import com.pissartel.entity.MapItemEntity
import kotlinx.coroutines.flow.StateFlow

fun interface GetProfileMapPicturesUseCase {
    suspend operator fun invoke(): StateFlow<List<MapItemEntity>?>
}