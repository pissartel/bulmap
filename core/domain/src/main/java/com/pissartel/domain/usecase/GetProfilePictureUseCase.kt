package com.pissartel.domain.usecase

import android.net.Uri
import kotlinx.coroutines.flow.StateFlow

fun interface GetProfilePictureUseCase {
    suspend operator fun invoke(): StateFlow<Uri?>
}