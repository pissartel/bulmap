package com.pissartel.detail

import MapItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.pissartel.ui.component.MapItem
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.pissartel.ui.component.MapItemMarker

@Composable
internal fun MapPictureDetailRoute(
    mapItemId: String,
    viewModel: MapPictureDetailViewModel = hiltViewModel<MapPictureDetailViewModel, MapPictureDetailViewModel.Factory> { factory ->
        factory.create(mapItemId)
    }
) {
    val state by viewModel.state.collectAsState()

    MapPictureDetailScreen(state.mapItem) {
        viewModel.toggleLike()
    }
}

@Composable
private fun MapPictureDetailScreen(
    mapItem: MapItem?,
    onLikeClick: () -> Unit
) {
    val pictureMapItemLocation by remember(mapItem?.latitude, mapItem?.longitude) {
        derivedStateOf {
            if (mapItem == null) {
                LatLng(48.856614, 2.352222) // Paris}
            } else {
                LatLng(mapItem.latitude, mapItem.longitude)
            }
        }
    }
    val markerState by remember(pictureMapItemLocation) {
        derivedStateOf {
            MarkerState(position = pictureMapItemLocation)
        }
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(pictureMapItemLocation, 14f) // Set zoom level
    }

    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        MapItem(mapItem, onLikeClick = onLikeClick)
        Spacer(modifier = Modifier.height(16.dp))

        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            cameraPositionState = cameraPositionState
        ) {
            mapItem?.let {
                MapItemMarker(
                    mapItem,
                    false,
                    onClick = {},
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}