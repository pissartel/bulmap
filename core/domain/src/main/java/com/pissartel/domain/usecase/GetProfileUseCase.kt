package com.pissartel.domain.usecase

import com.pissartel.entity.ProfileEntity
import kotlinx.coroutines.flow.StateFlow

fun interface GetProfileUseCase {
    suspend operator fun invoke(): StateFlow<ProfileEntity?>
}