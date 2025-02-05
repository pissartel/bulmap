package com.pissartel.domain.usecase

import android.net.Uri

fun interface CreateNewMapItemUseCase {
    suspend operator fun invoke(
        pictureUrl: Uri,
        latitude: Double,
        longitude: Double,
        pictureDescription: String,
    )
}