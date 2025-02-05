package com.pissartel.repolist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pissartel.detail.navigateToMapPictureDetail

const val feedScreenRoute = "feedScreenRoute"

fun NavGraphBuilder.feedScreen(navController: NavController) {
    composable(route = feedScreenRoute) {
        FeedRoute {
            navController.navigateToMapPictureDetail(it)
        }
    }
}