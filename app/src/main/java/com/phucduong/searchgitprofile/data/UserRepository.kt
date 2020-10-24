package com.phucduong.searchgitprofile.data

import android.content.SharedPreferences
import android.text.format.DateUtils
import com.phucduong.searchgitprofile.data.local.User

class UserRepository constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val sharedPreferences: SharedPreferences
) : Repository {
    private var cached: LinkedHashMap<String, List<User>> = LinkedHashMap()

    override suspend fun getUserListByKeyword(keyword: String): Result<List<User>> {
        if (cached.isNotEmpty()) {
            val listUser = cached[keyword]
            if (!listUser.isNullOrEmpty()) {
                return Result.Success(listUser)
            }
        }

        val result = localDataSource.getUserListByKeyword(keyword)
        if (result is Result.Success) {
            putDataToCached(keyword, result.data)
            return result
        }
        return getUserListFromRemote(keyword)
    }

    override suspend fun checkRefreshCached() {
        val lastTimeCached = sharedPreferences.getLong("cachedTime", System.currentTimeMillis())
        if (!DateUtils.isToday(lastTimeCached)) {
            clearLocalData()
        }
    }

    override fun putDataToCached(keyword: String, listUser: List<User>) {
        cached[keyword] = listUser
    }

    private suspend fun putDataToLocal(listUser: List<User>) {
        localDataSource.saveUserList(listUser)
        setCacheLocalTime()
    }

    private suspend fun getUserListFromRemote(keyword: String): Result<List<User>> {
        val result = remoteDataSource.getUserListByKeyword(keyword)
        if (result is Result.Success) {
            putDataToCached(keyword, result.data)
            putDataToLocal(result.data)
        }
        return result
    }

    private fun setCacheLocalTime() {
        val editor = sharedPreferences.edit()
        editor.putLong("cachedTime", System.currentTimeMillis())
        editor.apply()
    }

    private fun clearCachedTime() {
        val editor = sharedPreferences.edit()
        editor.remove("cachedTime")
        editor.apply()
    }

    private suspend fun clearLocalData() {
        localDataSource.clearData()
        cached.clear()
        clearCachedTime()
    }
}
