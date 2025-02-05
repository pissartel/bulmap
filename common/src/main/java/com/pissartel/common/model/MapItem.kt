import android.net.Uri
import com.pissartel.entity.MapItemEntity

data class MapItem(
    val id: String,
    val userAvatarUrl: Uri,
    val userName: String,
    val pictureUrl: Uri,
    val latitude: Double,
    val longitude: Double,
    val pictureDescription: String,
    val likeCount: Int,
    val likedByUser: Boolean
) {
    companion object {
        fun MapItemEntity.toMapItem(liked: Boolean) = MapItem(
            id = id,
            userAvatarUrl = userAvatarUrl,
            userName = userName,
            pictureUrl = pictureUrl,
            latitude = latitude,
            longitude = longitude,
            pictureDescription = pictureDescription,
            likeCount = likeCount,
            likedByUser = liked
        )
    }
}
