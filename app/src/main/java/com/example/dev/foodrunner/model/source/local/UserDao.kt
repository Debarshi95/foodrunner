package com.example.dev.foodrunner.model.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun saveUser(userEntity: UserEntity)

    @Query("SELECT * FROM USER_DATA")
    fun getUser(): UserEntity
}