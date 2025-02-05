package com.pissartel.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val mapScreenRoute = "mapScreenRoute"

fun NavController.navigateToMapScreen() {
    navigate(mapScreenRoute)
}

fun NavGraphBuilder.mapScreen(navController: NavController) {
    composable(route = mapScreenRoute) {
        MapScreenRoute() { navController.navigateToMapPictureDetail(it.id) }
    }
}