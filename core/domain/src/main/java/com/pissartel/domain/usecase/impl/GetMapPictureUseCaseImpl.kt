package com.pissartel.domain.usecase.impl

import com.pissartel.domain.repository.MapPictureRepository
import com.pissartel.domain.repository.ProfileRepository
import com.pissartel.domain.usecase.GetMapPictureUseCase
import com.pissartel.domain.usecase.GetProfilePictureUseCase
import com.pissartel.entity.MapItemEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn

internal class GetMapPictureUseCaseImpl(
    private val mapPictureRepository: MapPictureRepository,
    private val profileRepository: ProfileRepository
) : GetMapPictureUseCase {

    override suspend fun invoke(id: String): Flow<MapItemEntity?> {
        //for mock purpose
        return mapPictureRepository.getFeed()
            .combine(profileRepository.getMapPictureItems()) { list1, list2 ->
                if (list2 != null) list1 + list2 else list1
            }.map { it.find { it.id == id } }
    }
}