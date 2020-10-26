package com.phucduong.searchgitprofile.data

import com.phucduong.searchgitprofile.data.model.User
import com.phucduong.searchgitprofile.data.model.UserProfile

class UserRepository constructor(
    private val remoteDataSource: RemoteDataSource
) : Repository {
    override suspend fun searchUser(query: String, page: Int): Result<List<User>> {
        return remoteDataSource.searchUser(query, page)
    }

    override suspend fun getUserProfile(userName: String): Result<UserProfile> {
        return remoteDataSource.getUserProfile(userLoginName = userName)
    }
}
