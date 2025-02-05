package com.pissartel.picsocialapp.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.pissartel.detail.mapItemDetailScreen
import com.pissartel.detail.mapScreenRoute
import com.pissartel.detail.navigateToMapPictureDetail
import com.pissartel.repolist.feedScreen
import com.pissartel.repolist.feedScreenRoute

object FeedTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.List)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Feed",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = feedScreenRoute,
        ) {
            feedScreen {
                navController.navigateToMapPictureDetail(it)
            }
            mapItemDetailScreen()
        }
    }
}