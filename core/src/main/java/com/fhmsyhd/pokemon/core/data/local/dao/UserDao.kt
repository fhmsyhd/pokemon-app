package com.fhmsyhd.pokemon.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fhmsyhd.pokemon.core.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user_table WHERE username = :username LIMIT 1")
    suspend fun getUser(username: String): UserEntity?

    @Query("DELETE FROM user_table")
    suspend fun clearUsers()
}