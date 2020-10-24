package com.phucduong.searchgitprofile.data

import com.phucduong.searchgitprofile.data.local.User

interface Repository {
    suspend fun getUserListByKeyword(keyword: String): Result<List<User>>
    suspend fun checkRefreshCached()
    fun putDataToCached(keyword: String, listWeather: List<User>)
}