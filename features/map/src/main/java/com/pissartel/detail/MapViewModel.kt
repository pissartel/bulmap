package com.pissartel.detail

import MapItem
import MapItem.Companion.toMapItem
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.pissartel.common.base.BaseViewModel
import com.pissartel.common.base.UiEffect
import com.pissartel.common.base.UiState
import com.pissartel.domain.usecase.AddLikeUseCase
import com.pissartel.domain.usecase.CreateNewMapItemUseCase
import com.pissartel.domain.usecase.GetCloseMapPicturesUseCase
import com.pissartel.domain.usecase.GetLikesUseCase
import com.pissartel.domain.usecase.GetProfileMapPicturesUseCase
import com.pissartel.domain.usecase.GetProfilePictureUseCase
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
class MapViewModel @Inject constructor(
    private val getProfilePictureUseCase: GetProfilePictureUseCase,
    private val getLikesUseCase: GetLikesUseCase,
    private val addLikeUseCase: AddLikeUseCase,
    private val removeLikeUseCase: RemoveLikeUseCase,
    private val getCloseMapPicturesUseCase: GetCloseMapPicturesUseCase,
    private val getProfileMapPicturesUseCase: GetProfileMapPicturesUseCase,
    private val createNewMapItemUseCase: CreateNewMapItemUseCase
) : BaseViewModel<MapUiState, MapUiEffect>() {

    init {
        fetchCloseMapPicturesUseCase()
        observeLike()
        observeProfilePicture()
        observeProfileMapPictures()
    }

    fun fetchUserLocation(context: Context, fusedLocationClient: FusedLocationProviderClient) {
        // Check if the location permission is granted
        if (ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                // Fetch the last known location
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        // Update the user's location in the state
                        val userLatLng = LatLng(it.latitude, it.longitude)
                        setState { copy(userLocation = userLatLng) }
                    }
                }
            } catch (e: SecurityException) {
                Timber.tag("pi/")
                    .e("Permission for location access was revoked: ${e.localizedMessage}")
            }
        } else {
            Timber.tag("pi/").e("Location permission is not granted.")
        }
    }

    fun createNewMapItem(
        pictureUrl: Uri
    ) {
        viewModelScope.launch {
            createNewMapItemUseCase(
                pictureUrl,
                currentState.userLocation.latitude,
                currentState.userLocation.longitude,
                "Hey"
            ) // todo caption
        }
    }

    fun toggleLike(id: String) {
        viewModelScope.launch {
            val mapItems = currentState.mapPictures.toMutableList()
            val item = mapItems.find { it.id == id }
            if (item?.likedByUser == true) {
                removeLikeUseCase(id)
            } else addLikeUseCase(id)
        }
    }

    private fun observeProfilePicture() {
        viewModelScope.launch {
            getProfilePictureUseCase().collectLatest {
                setState {
                    copy(
                        userProfilePicture = it,
                    )
                }
            }
        }
    }

    private fun observeLike() {
        viewModelScope.launch {
            getLikesUseCase().collectLatest { likes ->
                if (likes != null) {
                    val mapItems = currentState.mapPictures.toMutableList()
                    setState {
                        copy(mapPictures = mapItems.map {
                            it.copy(
                                likedByUser = likes.contains(it.id)
                            )
                        })
                    }
                }
            }
        }
    }

    private fun observeProfileMapPictures() {
        viewModelScope.launch {
            getProfileMapPicturesUseCase().collectLatest { profileMapItems ->
                if (profileMapItems != null) {
                    val likes = getLikesUseCase().value
                    val mapItems = currentState.mapPictures.toMutableList()
                    mapItems.addAll(profileMapItems.map {
                        it.toMapItem(liked = likes?.contains(it.id) ?: false)
                    })
                    setState {
                        copy(mapPictures = mapItems)
                    }
                }
            }
        }
    }


    private fun fetchCloseMapPicturesUseCase() {
        viewModelScope.launch {
            getCloseMapPicturesUseCase(
                currentState.userLocation.latitude,
                currentState.userLocation.longitude
            ).asResult().doOnSuccess {
                val likes = getLikesUseCase().value
                setState {
                    copy(mapPictures = it.map {
                        it.toMapItem(
                            liked = likes?.contains(it.id) ?: false
                        )
                    })
                }
            }.collect()
        }
    }

    override fun createInitialState(): MapUiState =
        MapUiState(null, LatLng(48.856614, 2.352222)) // Paris
}

data class MapUiState(
    val userProfilePicture: Uri?,
    val userLocation: LatLng,
    val mapPictures: List<MapItem> = emptyList()
) : UiState

sealed interface MapUiEffect : UiEffect {}