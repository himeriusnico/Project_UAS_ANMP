package com.ubaya.project_uas.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    fun login(username: String, password: String): User?

    @Query("UPDATE users SET password = :newPassword WHERE id = :userId")
    fun updatePassword(userId: Int, newPassword: String)

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    fun getUserById(id: Int): User?

    @Update
    fun update(user: User)
}

