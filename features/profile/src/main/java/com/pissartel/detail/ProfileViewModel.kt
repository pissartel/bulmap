package com.pissartel.detail

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.pissartel.common.base.BaseViewModel
import com.pissartel.common.base.UiEffect
import com.pissartel.common.base.UiState
import com.pissartel.domain.usecase.GetProfileMapPicturesUseCase
import com.pissartel.domain.usecase.GetProfilePictureUseCase
import com.pissartel.domain.usecase.GetProfileUseCase
import com.pissartel.domain.utils.asResult
import com.pissartel.domain.utils.doOnSuccess
import com.pissartel.entity.MapItemEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfilePictureUseCase: GetProfilePictureUseCase,
    private val getProfileMapPicturesUseCase: GetProfileMapPicturesUseCase,
    private val getProfileUseCase: GetProfileUseCase
) : BaseViewModel<ProfileUiState, ProfileUiEffect>() {

    init {
        fetchProfile()
        observeProfilePicture()
        observeProfileMapPictures()
    }

    private fun observeProfileMapPictures() {
        viewModelScope.launch {
            getProfileMapPicturesUseCase().collectLatest { profileMapItems ->
                setState {
                    copy(mapPictures = profileMapItems)
                }
            }
        }
    }

    private fun observeProfilePicture() {
        viewModelScope.launch {
            getProfilePictureUseCase().collectLatest {
                setState {
                    copy(
                        userAvatar = it,
                    )
                }
            }
        }
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            getProfileUseCase()
                .asResult()
                .doOnSuccess {
                    setState {
                        copy(
                            userName = it?.userName,
                            about = it?.about,
                            followerCount = it?.followerCount,
                            followingCount = it?.followingCount
                        )
                    }
                }
                .collect()
        }
    }

    override fun createInitialState(): ProfileUiState = ProfileUiState()
}


data class ProfileUiState(
    val userAvatar: Uri? = null,
    val userName: String? = null,
    val about: String? = null,
    val followerCount: Int? = null,
    val followingCount: Int? = null,
    val mapPictures: List<MapItemEntity>? = null
) : UiState

sealed interface ProfileUiEffect : UiEffect {
}