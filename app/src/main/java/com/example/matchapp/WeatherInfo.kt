package com.example.matchapp

import android.app.Application

class WeatherInfo:Application() {

    var QRResult: MutableList<String>? = null
    var GVweather = mutableMapOf<String,List<String>>() // mmmが入ってくる

    companion object {
        private var instance : WeatherInfo? = null

        fun  getInstance(): WeatherInfo {
            if (instance == null)
                instance = WeatherInfo()

            return instance!!
        }

    }
}