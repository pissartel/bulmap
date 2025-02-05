package com.pissartel.domain.usecase

import kotlinx.coroutines.flow.StateFlow

fun interface GetLikesUseCase {
    suspend operator fun invoke(): StateFlow<List<String>?>
}