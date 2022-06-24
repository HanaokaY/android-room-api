package com.example.matchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val WeatherAllDatas = WeatherInfo.getInstance().GVweather
        var count:Int = 0
        val WeatherStatus = mapOf<String,String>("Clouds" to "まあ、曇りだわ","Rain" to "まあ雨だわ","Clear" to "晴れだわ")
        val ViewPartsArray = arrayOf(
            findViewById<TextView>(R.id.name),
            findViewById<TextView>(R.id.tvCityWeather),
            findViewById<TextView>(R.id.tvMax),
            findViewById<TextView>(R.id.tvMin),
            findViewById<TextView>(R.id.tvCityWeatherDetail)
        )

        //ホントは!!は使わないほうが良いから、下のinfosをしっかり型宣言?したほうが良い。
        var infos = listOf<String>()
        infos = WeatherAllDatas[intent.getStringExtra("CITY")]!!//←の!!がつかない状態でエラーがないのが理想。

        ViewPartsArray.forEach{ viewobj ->
            viewobj.text = WeatherStatus[infos[count]] ?: infos[count] ?: "情報を取得できませんでした"
            count ++
        }

        //アニメーション
        val fadeAnim = AlphaAnimation(0.0f,1.0f)
        fadeAnim.duration = 2000
        ViewPartsArray.map{ it.animation = fadeAnim }
    }
}