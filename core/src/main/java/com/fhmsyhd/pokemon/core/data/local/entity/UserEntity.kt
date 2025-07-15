package com.fhmsyhd.pokemon.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fhmsyhd.pokemon.core.util.Constant.TABLE_USER

@Entity(tableName = TABLE_USER)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String
)