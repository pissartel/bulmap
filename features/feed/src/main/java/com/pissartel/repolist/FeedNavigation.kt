package com.pissartel.repolist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val feedScreenRoute = "feedScreenRoute"

fun NavGraphBuilder.feedScreen(
    onNavigateToDetail: (String) -> Unit
) {
    composable(route = feedScreenRoute) {
        FeedRoute(onNavigateToDetail = onNavigateToDetail)
    }
}