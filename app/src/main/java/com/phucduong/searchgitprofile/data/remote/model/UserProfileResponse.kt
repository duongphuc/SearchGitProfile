package com.phucduong.searchgitprofile.data.remote.model

import com.squareup.moshi.Json

data class UserProfileResponse(

    @Json(name = "followers")
    val followers: Int,

    @Json(name = "avatar_url")
    val avatar_url: String,

    @Json(name = "following")
    val following: Int,

    @Json(name = "name")
    val name: String? = "",

    @Json(name = "company")
    val company: String?,

    @Json(name = "location")
    val location: String?,

    @Json(name = "login")
    val login: String?,

    @Json(name = "email")
    val email: String?
)
