package com.phucduong.searchgitprofile.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE keyword = :keyword")
    suspend fun getUserByKeyWord(keyword: String): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(listUser: List<User>)

    @Query("DELETE FROM User")
    fun clearData()
}