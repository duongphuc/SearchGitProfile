package com.phucduong.searchgitprofile.data.remote

import com.phucduong.searchgitprofile.data.remote.model.SearchListResponse
import com.phucduong.searchgitprofile.data.remote.model.UserProfileResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("search/users")
    fun searchUser(
        @Query("q") query: String,
        @Query("page") pageNumber: Int
    ): Deferred<Response<SearchListResponse>>

    @GET("/users/{login}")
    fun getUserProfile(
        @Path("login") login: String
    ): Deferred<Response<UserProfileResponse>>
}