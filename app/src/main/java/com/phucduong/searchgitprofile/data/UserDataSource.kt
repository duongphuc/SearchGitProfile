package com.phucduong.searchgitprofile.data

import com.phucduong.searchgitprofile.data.model.User
import com.phucduong.searchgitprofile.data.model.UserProfile

interface RemoteDataSource {
    suspend fun searchUser(keyword: String, page: Int): Result<List<User>>
    suspend fun getUserProfile(userLoginName: String): Result<UserProfile>
}