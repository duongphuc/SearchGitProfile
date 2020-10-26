package com.phucduong.searchgitprofile.data.model

data class User constructor(
    val login: String,
    val avatarUrl: String?,
    val idGit: Int,
    val url: String,
    val type: String,
    val score: Int,
    val query: String
) {
}