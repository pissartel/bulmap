package com.pissartel.picsocialapp.navigation.tabs

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Portrait
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil.compose.rememberAsyncImagePainter
import com.pissartel.detail.mapItemDetailScreen
import com.pissartel.domain.usecase.GetProfilePictureUseCase
import com.pissartel.detail.profileScreen
import com.pissartel.detail.profileScreenRoute
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

object ProfileTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Portrait)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Profile",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = profileScreenRoute,
        ) {
            profileScreen(navController)
            mapItemDetailScreen()
        }
    }
}


@Composable
fun RowScope.ProfileTabNavigationItem(
    getProfilePictureUseCase: GetProfilePictureUseCase,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.colors(),
) {
    val tabNavigator = LocalTabNavigator.current
    val interactionSource = remember { MutableInteractionSource() }

    val selected = tabNavigator.current == ProfileTab

    val scope = rememberCoroutineScope()

    var profilePicture by remember {
        mutableStateOf<Uri?>(null)
    }

    DisposableEffect(Unit) {
        val job = scope.launch {
            getProfilePictureUseCase().collectLatest {
                profilePicture = it
            }
        }
        onDispose {
            job.cancel()
        }
    }

    val icon =
        if (profilePicture != null) {
            rememberAsyncImagePainter(profilePicture)
        } else {
            rememberVectorPainter(Icons.Default.Portrait)
        }

    Box(
        Modifier
            .selectable(
                selected = selected,
                onClick = {
                    tabNavigator.current = ProfileTab
                },
                role = Role.Tab,
                interactionSource = interactionSource,
                indication = null,
            )
            .size(64.dp)
            .weight(1f),
        contentAlignment = Alignment.Center,
        propagateMinConstraints = true,
    ) {
        Image(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(40.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = if (selected) colors.selectedIconColor else Color.Transparent,
                    shape = CircleShape
                )
            ,
            painter = icon,
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
    }
}