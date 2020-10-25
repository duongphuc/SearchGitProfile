package com.phucduong.searchgitprofile.data.remote.model

import com.squareup.moshi.Json

data class SearchListResponse(
    @Json(name = "items")
    val items: List<UserResponse> = ArrayList()
)

data class UserResponse(

    @Json(name = "score")
    val score: Int,

    @Json(name = "avatar_url")
    val avatar_url: String,

    @Json(name = "id")
    val id: Int,

    @Json(name = "login")
    val login: String,

    @Json(name = "type")
    val type: String,

    @Json(name = "url")
    val url: String
)
