package com.pissartel.data.repoimpl

import android.net.Uri
import com.pissartel.domain.repository.MapPictureRepository
import com.pissartel.entity.MapItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class MapPictureRepoImpl @Inject constructor() : MapPictureRepository {

    override suspend fun getCloseMapPictures(
        latitude: Double,
        longitude: Double
    ): Flow<List<MapItemEntity>> = flow {
        emit(data.filter {
            haversine(latitude, longitude, it.latitude, it.longitude) <= RADIUS_KM
        })
    }

    override suspend fun getFeed(): Flow<List<MapItemEntity>> = flow {
        emit(data)
    }

    // For mock purpose
    private val data = listOf(
        MapItemEntity(
            userAvatarUrl = Uri.parse("https://dragonball13530.wordpress.com/wp-content/uploads/2015/06/young-goku-sangoku-petit.png"),
            userName = "Goku",
            pictureUrl = Uri.parse("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a8/Tour_Eiffel_Wikimedia_Commons.jpg/800px-Tour_Eiffel_Wikimedia_Commons.jpg"),
            pictureDescription = "Effeil Tower ",
            latitude = 48.858370,
            longitude = 2.294481,
            likeCount = 500
        ),
        MapItemEntity(
            userAvatarUrl = Uri.parse("https://static.hitek.fr/img/bas/ill_m/2144102202/8067b_screenshot20250113at144311cell2ndformbyjohnny120588dfc5kwdfullview.jpgimagejpeg12801707pixels1.webp"),
            userName = "Cell",
            pictureUrl = Uri.parse("https://upload.wikimedia.org/wikipedia/commons/7/75/Montmarte_2_%28pixinn.net%29.jpg"),
            pictureDescription = "Montmartre",
            latitude = 48.886143,
            longitude = 2.343124,
            likeCount = 100
        ),
        MapItemEntity(
            userAvatarUrl = Uri.parse("https://i-mom.unimedias.fr/2022/07/19/sans_titre.png?auto=format%2Ccompress&crop=faces&cs=tinysrgb&fit=crop&h=501&w=890"),
            userName = "Vegeta",
            pictureUrl = Uri.parse("https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/Bordeaux_-_Juillet_2012_%2885%29.JPG/1920px-Bordeaux_-_Juillet_2012_%2885%29.JPG"),
            pictureDescription = "Mirroir d'eau",
            latitude = 44.841730,
            longitude = -0.569074,
            likeCount = 1000
        )
    )

    private fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0 // earth radius in km
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return R * c
    }

    companion object {
        private const val RADIUS_KM = 5
    }
}