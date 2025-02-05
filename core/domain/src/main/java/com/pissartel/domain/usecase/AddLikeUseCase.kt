package com.pissartel.domain.usecase

fun interface AddLikeUseCase {
    suspend operator fun invoke(mapItemId: String)
}