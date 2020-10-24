package com.phucduong.searchgitprofile.data.local

import com.phucduong.searchgitprofile.data.LocalDataSource
import com.phucduong.searchgitprofile.data.Result
import com.phucduong.searchgitprofile.data.remote.model.ErrorResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserLocalDataSource(
    private val userDao: UserDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LocalDataSource {
    override suspend fun saveUserList(listUser: List<User>) = withContext(ioDispatcher) {
        userDao.insertUser(listUser)
    }

    override suspend fun clearData() {
        withContext(ioDispatcher) {
            userDao.clearData()
        }
    }

    override suspend fun getUserListByKeyword(keyword: String): Result<List<User>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val listUser = userDao.getUserByKeyWord(keyword)
                if (listUser.isNullOrEmpty()) {
                    Result.Error(ErrorResponse(0, "Data not available"))
                } else {
                    Result.Success(userDao.getUserByKeyWord(keyword))
                }
            } catch (e: Exception) {
                Result.UnKnowError(e)
            }
        }
}