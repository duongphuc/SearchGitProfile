package com.phucduong.searchgitprofile.data.remote

import com.phucduong.searchgitprofile.data.RemoteDataSource
import com.phucduong.searchgitprofile.data.Result
import com.phucduong.searchgitprofile.data.local.User
import com.phucduong.searchgitprofile.data.remote.model.ErrorResponse
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException

class UserRemoteDataSource constructor(private val apiServices: ApiServices) :
    RemoteDataSource {
    override suspend fun getUserListByKeyword(keyword: String): Result<List<User>> {
//        val weatherResponseResult = safeApiResult {
//            apiServices.getWeatherByCity(
//                keyword,
//                Constant.DEFAULT_NUMBER_OF_DAY,
//                Constant.DEFAULT_UNITS
//            ).await()
//        }
//
//        return when (weatherResponseResult) {
//            is Result.Success -> {
//                val weatherResponse = weatherResponseResult.data
//                val listMappedWeather = weatherResponse.mapToListWeather(keyword)
//                Result.Success(listMappedWeather)
//            }
//            is Result.UnKnowError -> weatherResponseResult
//            is Result.NetWorkError -> weatherResponseResult
//            is Result.Error -> weatherResponseResult
//        }
        return Result.Success(mutableListOf())
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