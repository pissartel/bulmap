package com.pissartel.ui.component

import MapItem
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.pissartel.ui.R

@Composable
fun MapItem(
    mapItem: MapItem?,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier,
    onItemClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .clickable(enabled = onItemClick != null) {
                if (onItemClick != null) {
                    onItemClick()
                }
            },
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(shimmerBrush(mapItem == null), shape = CircleShape)
                ) {
                    if (mapItem != null) {
                        Image(
                            painter = rememberAsyncImagePainter(model = mapItem.userAvatarUrl),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .aspectRatio(1f)
                                .clip(CircleShape)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .sizeIn(60.dp, 30.dp)
                        .background(
                            shimmerBrush(mapItem == null),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (mapItem != null) {
                        Text(text = mapItem.userName, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(shimmerBrush(mapItem == null), shape = RoundedCornerShape(8.dp))
            ) {
                if (mapItem != null) {
                    Image(
                        painter = rememberAsyncImagePainter(model = mapItem.pictureUrl),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(shimmerBrush(mapItem == null), shape = CircleShape)
                    ) {
                        if (mapItem != null) {
                            androidx.compose.material3.Icon(
                                painter = painterResource(
                                    if (mapItem.likedByUser) R.drawable.heart_full
                                    else R.drawable.heart
                                ),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable { onLikeClick() }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .sizeIn(40.dp, 20.dp)
                            .background(
                                shimmerBrush(mapItem == null),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (mapItem != null) {
                            Text(
                                text = "${mapItem.likeCount + if (mapItem.likedByUser) 1 else 0}",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            if (mapItem != null) {
                Text(
                    text = mapItem.pictureDescription,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
private fun MapItemPreview() {
    val mapItem = MapItem(
        id = "",
        userAvatarUrl = Uri.parse("https://dragonball13530.wordpress.com/wp-content/uploads/2015/06/young-goku-sangoku-petit.png"),
        userName = "Goku",
        pictureUrl = Uri.EMPTY,
        pictureDescription = "Effeil Tower ",
        latitude = 48.858370,
        longitude = 2.294481,
        likeCount = 500,
        likedByUser = true
    )

    MaterialTheme {
        com.pissartel.ui.component.MapItem(mapItem, {}) {}
    }
}

@Preview
@Composable
private fun MapItemLoadingPreview() {

    MaterialTheme {
        com.pissartel.ui.component.MapItem(null, {}) {}
    }
}