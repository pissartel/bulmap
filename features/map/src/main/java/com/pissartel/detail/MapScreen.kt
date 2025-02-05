package com.pissartel.detail

import MapItem
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.Shape
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.pissartel.designsystem.theme.bulmaColors
import com.pissartel.ui.component.MapItemMarker
import com.pissartel.ui.component.MapUserMarker
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

@RequiresApi(Build.VERSION_CODES.FROYO)
@Composable
internal fun MapScreenRoute(
    viewModel: MapViewModel = hiltViewModel(),
    onMapItemClick: (MapItem) -> Unit
) {
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.fetchUserLocation(context, fusedLocationClient)
        } else {
            Timber.tag("pi/").e("Location permission was denied by the user.")
        }
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val cameraPictureLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { success ->
                if (success) {
                    imageUri?.let { viewModel.createNewMapItem(it) }
                }
            })


// Request the location permission when the composable is launched
    LaunchedEffect(Unit) {
        if (
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            // Fetch the user's location and update the camera
            viewModel.fetchUserLocation(context, fusedLocationClient)
        } else {
            // Request the location permission if it has not been granted
            permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    MapScreen(
        state,
        onMapItemLikeClick = { viewModel.toggleLike(it) },
        onMapItemClick = onMapItemClick,
        onCameraClick = {
            scope.launch {
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED

                if (!hasPermission) {
                    permissionLauncher.launch(android.Manifest.permission.CAMERA)

                } else {
                    imageUri = createImageFile(context)
                    cameraPictureLauncher.launch(imageUri)
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.FROYO)
@Composable
private fun MapScreen(
    state: MapUiState,
    onMapItemLikeClick: (String) -> Unit,
    onMapItemClick: (MapItem) -> Unit,
    onCameraClick: () -> Unit
) {
    val userLocation by remember(state.userLocation) {
        derivedStateOf {
            LatLng(state.userLocation.latitude, state.userLocation.longitude)
        }
    }

    val cameraPositionState = rememberCameraPositionState() {
        position = CameraPosition.fromLatLngZoom(userLocation, 14f) // Set zoom level
    }

    var selectedMapItemId by remember {
        mutableStateOf<String?>(null)
    }

    Box {
        GoogleMap(
            modifier = Modifier.fillMaxSize(), // Fill the entire screen
            cameraPositionState = cameraPositionState
        ) {
            UserMarker(state.userProfilePicture, state.userLocation) {}

            state.mapPictures.forEach { mapItemEntity ->
                MapItemMarker(
                    mapItemEntity,
                    selectedMapItemId == mapItemEntity.id,
                    color = bulmaColors.cyan,
                    onLikeClick = { onMapItemLikeClick(mapItemEntity.id) }
                ) {
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(
                        LatLng(
                            mapItemEntity.latitude,
                            mapItemEntity.longitude
                        ), 14f
                    )
                    if (selectedMapItemId == mapItemEntity.id) {
                        onMapItemClick(mapItemEntity)
                    } else {
                        selectedMapItemId = mapItemEntity.id
                    }
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            onClick = onCameraClick,
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
            shape = CircleShape
        ) {
            Icon(
                painter = rememberVectorPainter(Icons.Default.CameraAlt),
                contentDescription = ""
            )
        }
    }

    LaunchedEffect(userLocation) {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(userLocation, 14f)
    }
}

@Composable
fun UserMarker(
    pictureUrl: Uri?,
    location: LatLng, onClick: () -> Unit
) {
    val markerState by remember(location) {
        derivedStateOf {
            MarkerState(position = location)
        }
    }

    MapUserMarker(pictureUrl, markerState, "Me", onClick, bulmaColors.pink)
}

@RequiresApi(Build.VERSION_CODES.FROYO)
private fun createImageFile(context: Context): Uri {
    val storageDir: File? = context.getExternalFilesDir(null)
    val imageFile = File.createTempFile("JPEG_${System.currentTimeMillis()}_", ".jpg", storageDir)
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", imageFile)
}