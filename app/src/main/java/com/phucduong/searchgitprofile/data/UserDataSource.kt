package com.phucduong.searchgitprofile.data

import com.phucduong.searchgitprofile.data.local.User

interface RemoteDataSource {
    suspend fun getUserListByKeyword(keyword: String): Result<List<User>>
}

interface LocalDataSource {
    suspend fun getUserListByKeyword(keyword: String): Result<List<User>>
    suspend fun saveUserList(listUser: List<User>)
    suspend fun clearData()
}