package com.example.matchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val wi = WeatherInfo.getInstance()
        val weather = wi.GVweather
        val secondname = findViewById<TextView>(R.id.name)
        val secondweather = findViewById<TextView>(R.id.tvCityWeather)
        val secondmax = findViewById<TextView>(R.id.tvMax)
        val secondmin = findViewById<TextView>(R.id.tvMin)

//        ホントは!!は使わないほうが良いから、下のinfosをしっかり型宣言したほうが良い。
        var infos = listOf<String>()
        infos = weather[intent.getStringExtra("CITY")]!!//←の!!がつかない状態でエラーがないのが理想。
//["さいたま","腫れ","22","11"]
        secondname.text = infos[0]
        secondweather.text = infos[1]
        secondmax.text = infos[2]
        secondmin.text = infos[3]

    }
}