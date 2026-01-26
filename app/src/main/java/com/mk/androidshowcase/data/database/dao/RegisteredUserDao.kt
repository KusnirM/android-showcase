package com.mk.androidshowcase.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mk.androidshowcase.data.database.entity.RegisteredUserEntity

@Dao
interface RegisteredUserDao {

    @Insert
    suspend fun insert(user: RegisteredUserEntity)

    @Query("SELECT * FROM registered_users WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): RegisteredUserEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM registered_users WHERE email = :email)")
    suspend fun emailExists(email: String): Boolean
}
