package com.pissartel.ui.component

import MapItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.pissartel.ui.R

@Composable
fun MapItemMarker(
    mapItem: MapItem,
    expanded: Boolean,
    color: Color,
    onLikeClick: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    val markerState by remember {
        derivedStateOf {
            MarkerState(position = LatLng(mapItem.latitude, mapItem.longitude))
        }
    }
    val picturePainter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(mapItem.pictureUrl)
            .allowHardware(false)
            .build()
    )

    val avatarPainter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(mapItem.userAvatarUrl)
            .allowHardware(false)
            .build()
    )


    MarkerComposable(
        keys = arrayOf(picturePainter.state, expanded, avatarPainter.state),
        state = markerState,
        title = mapItem.userName,
        zIndex = if (expanded) 1f else 0f,
        onClick = {
            if (onClick != null) {
                onClick()
                true
            } else false
        }
    ) {
        Box(
            modifier = Modifier
                .width(if (expanded) 160.dp else 48.dp)
                .clip(RoundedCornerShape(20.dp, 20.dp, 20.dp, 0.dp))
                .background(color)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            if (!expanded) {
                Image(
                    painter = picturePainter,
                    contentDescription = "Picture",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Column {
                    Image(
                        painter = picturePainter,
                        contentDescription = "Picture",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = avatarPainter,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(25.dp)
                                .aspectRatio(1f)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = mapItem.userName, style = MaterialTheme.typography.labelSmall)

                        Spacer(modifier = Modifier.weight(1f))

                        androidx.compose.material3.Icon(
                            painter = painterResource(
                                if (mapItem.likedByUser) R.drawable.heart_full
                                else R.drawable.heart
                            ),
                            contentDescription = "",
                            modifier = Modifier
                                .size(10.dp)
                                .clickable(onLikeClick != null) { onLikeClick?.let { it() } }
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${mapItem.likeCount + if (mapItem.likedByUser) 1 else 0}",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }

                }
            }
        }
    }
}
