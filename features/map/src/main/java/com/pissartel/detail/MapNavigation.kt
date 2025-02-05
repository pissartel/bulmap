package com.pissartel.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val mapScreenRoute = "mapScreenRoute"

fun NavController.navigateToMapScreen() {
    navigate(mapScreenRoute)
}

fun NavGraphBuilder.mapScreen(onNavigateToDetail: (String) -> Unit) {
    composable(route = mapScreenRoute) {
        MapScreenRoute(onNavigateToDetail=onNavigateToDetail)
    }
}