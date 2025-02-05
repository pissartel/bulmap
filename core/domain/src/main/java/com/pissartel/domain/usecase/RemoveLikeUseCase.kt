package com.pissartel.domain.usecase

fun interface RemoveLikeUseCase {
    suspend operator fun invoke(mapItemId: String)
}