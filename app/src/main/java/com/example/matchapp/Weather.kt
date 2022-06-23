package com.example.matchapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Weather(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val weather: String,
    val temp_max: Int,
    val temp_min: Int
    )