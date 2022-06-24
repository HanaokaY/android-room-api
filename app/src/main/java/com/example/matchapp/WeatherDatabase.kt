package com.example.matchapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Weather::class], version = 1,exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDAO
    //companionオブジェクトはクラスのインスタンス化無しで、データベースを作成または取得するためのメソッドへのクライアントからのアクセスを可能にする
    companion object {

        var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase {
            // エルビス演算子
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "data_table"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance

                return instance
            }
        }
    }
}