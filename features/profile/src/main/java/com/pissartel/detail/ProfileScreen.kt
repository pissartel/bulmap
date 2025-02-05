package com.pissartel.detail

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.pissartel.designsystem.theme.LocaleBulmaColors

@Composable
internal fun ProfileScreenRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
    onMapPictureClick: (String) -> Unit
) {
    val profileUiState by viewModel.state.collectAsState()

    ProfileScreen(
        profileUiState = profileUiState, onMapPictureClick
    )
}

@Composable
private fun ProfileScreen(
    profileUiState: ProfileUiState,
    onMapPictureClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item(span = { GridItemSpan(currentLineSpan = 3) }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 16.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .size(100.dp)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                ) {
                    if (profileUiState.userAvatar != null) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = rememberAsyncImagePainter(model = profileUiState.userAvatar),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                        )
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    if (profileUiState.userName != null) {
                        Text(profileUiState.userName, style = MaterialTheme.typography.titleLarge)
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    if (profileUiState.followerCount != null
                        && profileUiState.followingCount != null
                    ) {
                        Column() {
                            Row {
                                Text(
                                    profileUiState.followerCount.toString(),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = LocaleBulmaColors.current.cyan,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "Followers",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            Row {
                                Text(
                                    profileUiState.followingCount.toString(),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = LocaleBulmaColors.current.pink,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "Following",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                        }
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    if (profileUiState.about != null) {
                        Text(profileUiState.about)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        if (profileUiState.mapPictures != null) {
            item(span = { GridItemSpan(currentLineSpan = 3) }) {
                Column {
                    Text("Gallery", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            items(profileUiState.mapPictures, span = { GridItemSpan(1) }) { mapPicture ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    onClick = { onMapPictureClick(mapPicture.id) },
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberAsyncImagePainter(model = mapPicture.pictureUrl),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                    )
                }
            }

            item(span = { GridItemSpan(currentLineSpan = 3) }) {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
@Preview
private fun ProfileScreenPreview(modifier: Modifier = Modifier) {
    MaterialTheme {
        ProfileScreen(
            ProfileUiState(
                userAvatar = Uri.parse("https://static.wikia.nocookie.net/heros/images/2/2a/Bulma_Infobox.png/revision/latest/scale-to-width-down/1000?cb=20200912075502&path-prefix=fr"),
                userName = "Bulma",
                about = "Engineer, second daughter of Capsule Corporation founder Dr. Brief. I invented the Dragon Radar.",
                followerCount = 1000,
                followingCount = 24
            ), {}
        )
    }
}


