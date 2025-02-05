package com.pissartel.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val mapItemDetailScreenRoute = "mapItemDetailScreenRoute/{id}"

fun NavController.navigateToMapPictureDetail(mapItemId: String) {
    navigate(mapItemDetailScreenRoute.replace("{id}", mapItemId))
}

fun NavGraphBuilder.mapItemDetailScreen() {
    composable(route = mapItemDetailScreenRoute) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")?:""
        MapPictureDetailRoute(id)
    }
}