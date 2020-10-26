package com.phucduong.searchgitprofile.data

import com.phucduong.searchgitprofile.data.model.User
import com.phucduong.searchgitprofile.data.model.UserProfile

interface Repository {
    suspend fun searchUser(query: String, page: Int): Result<List<User>>
    suspend fun getUserProfile(userName: String): Result<UserProfile>
}