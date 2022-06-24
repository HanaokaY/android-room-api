package com.example.matchapp

import android.app.Application

class WeatherInfo:Application() {

    var GVweather = mutableMapOf<String,List<String>>() // WeatherAllDatasが入ってくる

    companion object {
        private var instance : WeatherInfo? = null

        fun  getInstance(): WeatherInfo {
            if (instance == null)
                instance = WeatherInfo()

            return instance!!
        }

    }
}