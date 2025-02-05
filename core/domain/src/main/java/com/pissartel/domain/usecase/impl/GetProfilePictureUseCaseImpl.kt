package com.pissartel.domain.usecase.impl

import android.net.Uri
import com.pissartel.domain.repository.ProfileRepository
import com.pissartel.domain.usecase.GetProfilePictureUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class GetProfilePictureUseCaseImpl(
    private val profileRepository: ProfileRepository
) : GetProfilePictureUseCase {
    override suspend fun invoke(): StateFlow<Uri?> =
        profileRepository.getProfile().map { it?.userAvatar }.stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
}