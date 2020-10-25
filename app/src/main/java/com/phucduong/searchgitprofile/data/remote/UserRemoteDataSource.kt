package com.phucduong.searchgitprofile.data.remote

import com.phucduong.searchgitprofile.data.RemoteDataSource
import com.phucduong.searchgitprofile.data.Result
import com.phucduong.searchgitprofile.data.model.User
import com.phucduong.searchgitprofile.data.model.UserProfile
import com.phucduong.searchgitprofile.data.remote.model.ErrorResponse
import com.phucduong.searchgitprofile.util.mapToListUser
import com.phucduong.searchgitprofile.util.mapToUserProfile
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException

class UserRemoteDataSource constructor(private val apiServices: ApiServices) :
    RemoteDataSource {
    override suspend fun searchUser(keyword: String, page: Int): Result<List<User>> {
        val result = safeApiResult {
            apiServices.searchUser(
                keyword,
                page
            ).await()
        }

        return when (result) {
            is Result.Success -> {
                val listResponse = result.data
                val listMappedUser = listResponse.mapToListUser(keyword)
                Result.Success(listMappedUser)
            }
            is Result.UnKnowError -> result
            is Result.NetWorkError -> result
            is Result.Error -> result
        }
    }

    override suspend fun getUserProfile(userLoginName: String): Result<UserProfile> {
        val result = safeApiResult {
            apiServices.getUserProfile(userLoginName).await()
        }
        return when (result) {
            is Result.Success -> Result.Success(result.data.mapToUserProfile())
            is Result.UnKnowError -> result
            is Result.NetWorkError -> result
            is Result.Error -> result
        }
    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>
    ): Result<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) return Result.Success(response.body()!!)
            val errorResponse = convertErrorBody(response.errorBody())
            return Result.Error(errorResponse)
        } catch (e: Throwable) {
            when (e) {
                is IOException -> Result.NetWorkError(e)
                else -> Result.UnKnowError(e)
            }
        }
    }

    private fun convertErrorBody(errorBody: ResponseBody?): ErrorResponse? {
        return try {
            errorBody?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }
}