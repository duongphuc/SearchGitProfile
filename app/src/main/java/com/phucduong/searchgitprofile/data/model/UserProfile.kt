package com.phucduong.searchgitprofile.data.model


data class UserProfile(
    val followers: Int,
    val avatarUrl: String?,
    val following: Int,
    val name: String,
    val company: String,
    val location: String,
    val login: String,
    val email: String
)