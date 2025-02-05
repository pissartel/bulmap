package com.pissartel.detail

import MapItem
import MapItem.Companion.toMapItem
import androidx.lifecycle.viewModelScope
import com.pissartel.common.base.BaseViewModel
import com.pissartel.common.base.UiEffect
import com.pissartel.common.base.UiState
import com.pissartel.domain.usecase.AddLikeUseCase
import com.pissartel.domain.usecase.GetLikesUseCase
import com.pissartel.domain.usecase.GetMapPictureUseCase
import com.pissartel.domain.usecase.RemoveLikeUseCase
import com.pissartel.domain.utils.asResult
import com.pissartel.domain.utils.doOnSuccess
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MapPictureDetailViewModel.Factory::class)
class MapPictureDetailViewModel @AssistedInject constructor(
    @Assisted val mapItemId: String,
    private val getMapPictureUseCase: GetMapPictureUseCase,
    private val getLikesUseCase: GetLikesUseCase,
    private val addLikeUseCase: AddLikeUseCase,
    private val removeLikeUseCase: RemoveLikeUseCase,
) : BaseViewModel<MapPictureDetailUiState, MapPictureDetailUiEffect>() {

    init {
        fetchMapPicture()
        observeLike()
    }

    @AssistedFactory
    interface Factory {
        fun create(mapItemId: String): MapPictureDetailViewModel
    }

    fun toggleLike() {
        viewModelScope.launch {
            val mapItem = currentState.mapItem ?: return@launch
            if (mapItem.likedByUser) {
                removeLikeUseCase(mapItem.id)
            } else addLikeUseCase(mapItem.id)
        }
    }

    private fun fetchMapPicture() {
        viewModelScope.launch {
            getMapPictureUseCase(mapItemId)
                .asResult()
                .doOnSuccess {
                    val likes = getLikesUseCase().value
                    if (it != null ) {
                        setState {
                            copy(mapItem = it.toMapItem(likes?.contains(it.id) == true))
                        }
                    }
                }.collect()
        }
    }

    private fun observeLike() {
        viewModelScope.launch {
            getLikesUseCase().collectLatest { likes ->
                val mapItem = currentState.mapItem
                if (likes != null && mapItem != null) {
                    setState {
                        copy(
                            mapItem = mapItem.copy(
                                likedByUser = likes.contains(mapItem.id)
                            )
                        )
                    }
                }
            }
        }
    }

    override fun createInitialState(): MapPictureDetailUiState = MapPictureDetailUiState(null)
}

data class MapPictureDetailUiState(
    val mapItem: MapItem?
) : UiState

sealed interface MapPictureDetailUiEffect : UiEffect {}