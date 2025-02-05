package com.pissartel.repolist

import MapItem
import MapItem.Companion.toMapItem
import androidx.lifecycle.viewModelScope
import com.pissartel.common.base.BaseViewModel
import com.pissartel.common.base.UiEffect
import com.pissartel.common.base.UiState
import com.pissartel.domain.usecase.AddLikeUseCase
import com.pissartel.domain.usecase.GetFeedUseCase
import com.pissartel.domain.usecase.GetLikesUseCase
import com.pissartel.domain.usecase.RemoveLikeUseCase
import com.pissartel.domain.utils.asResult
import com.pissartel.domain.utils.doOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getLikesUseCase: GetLikesUseCase,
    private val addLikeUseCase: AddLikeUseCase,
    private val removeLikeUseCase: RemoveLikeUseCase,
    private val getFeedUseCase: GetFeedUseCase
) : BaseViewModel<FeedUiState, FeedUiEffect>() {

    init {
        fetchProfile()
        observeLike()
    }

    fun toggleLike(id: String) {
        viewModelScope.launch {
            val mapItems = currentState.mapItems.toMutableList()
            val item = mapItems.find { it.id == id }
            if (item?.likedByUser == true) {
                removeLikeUseCase(id)
            } else addLikeUseCase(id)
        }
    }

    private fun observeLike() {
        viewModelScope.launch {
            getLikesUseCase().collectLatest { likes ->
                if (likes != null) {
                    val mapItems = currentState.mapItems.toMutableList()
                    setState {
                        copy(mapItems = mapItems.map {
                            it.copy(
                                likedByUser = likes.contains(it.id)
                            )
                        })
                    }
                }
            }
        }
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            getFeedUseCase()
                .asResult()
                .doOnSuccess { mapItemEntities ->
                    val likes = getLikesUseCase().value
                    setState {
                        copy(mapItems = mapItemEntities.map {
                            it.toMapItem(
                                liked = likes?.contains(it.id) ?: false
                            )
                        })
                    }
                }
                .collect()
        }
    }

    override fun createInitialState(): FeedUiState = FeedUiState(emptyList())
}


data class FeedUiState(
    val mapItems: List<MapItem>
) : UiState

sealed interface FeedUiEffect : UiEffect {
}