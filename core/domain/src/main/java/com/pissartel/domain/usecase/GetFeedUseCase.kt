package com.pissartel.domain.usecase

import com.pissartel.entity.MapItemEntity
import kotlinx.coroutines.flow.Flow

fun interface GetFeedUseCase {
    suspend operator fun invoke(): Flow<List<MapItemEntity>>
}