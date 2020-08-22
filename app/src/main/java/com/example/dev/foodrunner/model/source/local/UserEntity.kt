package com.example.dev.foodrunner.model.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user_data")
data class UserEntity @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int = UUID.randomUUID().hashCode(),
    val name: String,
    val email: String,
    val mobileNumber: Int,
    val address: String
)