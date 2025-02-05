package com.pissartel.entity

import android.net.Uri

data class ProfileEntity(
    val userAvatar: Uri,
    val userName: String,
    val about: String,
    val followerCount: Int,
    val followingCount: Int,
) {
    // for Mock purpose
    constructor() : this(
        userAvatar = Uri.parse("https://cdn-www.konbini.com/files/2023/09/Feat-Bulma.jpg?width=1920&quality=75&format=webp"),
        userName = "Bulma",
        about = "Engineer, second daughter of Capsule Corporation founder Dr. Brief. I invented the Dragon Radar.",
        followerCount = 1000,
        followingCount = 24
    )
}
