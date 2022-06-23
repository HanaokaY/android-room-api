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
        // Singleton パターンとは、そのクラスのインスタンスが1つしか生成されないことを保証するデザインパターン！DBのインスタンス化は処理重いため

        var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase {
            // インスタンスがなければ作る！あれば、あったものをリターン！
            return INSTANCE ?: synchronized(this) {
//synchronized()複数のスレッドが同じ処理を実行しようとしたときに、単一のスレッドだけが処理を実行できるように制御すること
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "data_table"
                )
                    .allowMainThreadQueries()
//データベースへの問い合わせを MainThread で実行できる様にする
//通常は MainThread でデータベースに問い合わせるとエラーになる！非同期処理できてない
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

//Roomとは、SQLiteを簡単に扱うようにDAOと言われるライブラリ
//メリデメまで考えれえるようになろうね！