package com.pissartel.repolist

import MapItem
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pissartel.ui.component.MapItem

@Composable
internal fun FeedRoute(
    onNavigateToDetail: (String) -> Unit,
    viewModel: FeedViewModel = hiltViewModel(),
) {
    val repoListUiSate by viewModel.state.collectAsStateWithLifecycle()

    FeedScreen(repoListUiSate, viewModel::toggleLike, onNavigateToDetail)
}

@Composable
fun FeedScreen(
    state: FeedUiState,
    onLikeClick: (String) -> Unit,
    onMapItemClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(items = state.mapItems) { mapItem ->
                MapItem(
                    mapItem = mapItem,
                    onLikeClick = { onLikeClick(mapItem.id) },
                    onItemClick = { onMapItemClick(mapItem.id) }
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
@Preview
private fun MapItemPreview(modifier: Modifier = Modifier) {
    MaterialTheme {
        val mapItem = MapItem(
            id = "",
            userAvatarUrl = Uri.parse("https://dragonball13530.wordpress.com/wp-content/uploads/2015/06/young-goku-sangoku-petit.png"),
            userName = "Goku",
            pictureUrl = Uri.parse("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a8/Tour_Eiffel_Wikimedia_Commons.jpg/800px-Tour_Eiffel_Wikimedia_Commons.jpg"),
            pictureDescription = "Effeil Tower ",
            latitude = 48.858370,
            longitude = 2.294481,
            likeCount = 500,
            likedByUser = true
        )
        MapItem(mapItem, {}) {

        }
    }
}


