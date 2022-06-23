package com.example.matchapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDAO {
    @Insert
//    fun insert(weather: Weather)
    fun insertAll( prince: List<Weather>)
    @Query("select * from weather")
    fun getAll(): List<Weather>

}