package com.pissartel.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val profileScreenRoute = "profileScreenRoute"

fun NavController.navigateToProfileScreen() {
    navigate(profileScreenRoute)
}

fun NavGraphBuilder.profileScreen(
    onNavigateToDetail: (String) -> Unit
) {
    composable(route = profileScreenRoute) {
        ProfileScreenRoute(onNavigateToDetail = onNavigateToDetail)
    }
}