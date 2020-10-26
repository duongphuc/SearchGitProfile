package com.phucduong.searchgitprofile

import com.phucduong.searchgitprofile.data.Repository
import com.phucduong.searchgitprofile.data.Result
import com.phucduong.searchgitprofile.data.model.User
import com.phucduong.searchgitprofile.data.model.UserProfile

class FakeRepository : Repository {
    companion object {
        val MOCK_LOGIN_USER_NAME = "test user"
        val MOCK_SEARCH_KEYWORD = "test"
        val MOCK_NAME = "abc"
        val MOCK_EMAIL = "abc@gmail.com"
        val MOCK_FOLLOWING = 1000
    }
    override suspend fun searchUser(query: String, page: Int): Result<List<User>> {
        return Result.Success(listOf(User(MOCK_LOGIN_USER_NAME, null, 0, "", "", 0, MOCK_SEARCH_KEYWORD)))
    }

    override suspend fun getUserProfile(userName: String): Result<UserProfile> {
        return Result.Success(UserProfile(0, null, MOCK_FOLLOWING, MOCK_NAME, "", "", MOCK_LOGIN_USER_NAME, MOCK_EMAIL))
    }

}