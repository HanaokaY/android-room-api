package com.example.matchapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiKey = "5fa5880256d9d005b553922279a1162f"
        val mainUrl = "https://api.openweathermap.org/data/2.5/weather?lang=ja"
        val prefectures_infos = listOf("tokyo","kanagawa","saitama","ibaraki","tochigi","chiba")
        var preference_name = mutableListOf<String>()
        var mmm = mutableMapOf<String,List<String>>()
        var mweatherList: ArrayList<Weather> = arrayListOf()
        val database = WeatherDatabase.getDatabase(this)
        val weatherDao = database.weatherDao()

        //マルチスレッド処理
        val client = OkHttpClient()
        lifecycleScope.launch {
            if(weatherDao.getAll().size == 0){
                prefectures_infos.forEach{ prefecture ->
                    var tmp = mutableListOf<String>()
                    val request = Request.Builder().apply {
                        url("$mainUrl&q=$prefecture&appid=$apiKey")
                    }.build()
                    val response = withContext(Dispatchers.IO) {
                        client.newCall(request).execute()
                    }//withContext end

                    val responseBody = response.body?.string().orEmpty()
                    val parentJsonObj = JSONObject(responseBody)
                    preference_name += parentJsonObj["name"].toString()

                    //["東京"]
                    //["東京","腫れ","22","11"]
                    //{"東京"=>["東京","腫れ","22","11"],"さいたま"=>["さいたま","腫れ","22","11"]}
                    tmp += parentJsonObj["name"].toString()
                    tmp += parentJsonObj.getJSONArray("weather").getJSONObject(0).getString("description")
                    tmp += (parentJsonObj.getJSONObject("main").getInt("temp_max")-273).toString()
                    tmp += (parentJsonObj.getJSONObject("main").getInt("temp_min")-273).toString()
                    mmm += parentJsonObj["name"].toString() to tmp
                    mweatherList += Weather(0,tmp[0],tmp[1],tmp[2].toInt(),tmp[3].toInt())

                }//forEach end

                System.out.println("データベースにInsert開始")
                weatherDao.insertAll(mweatherList)

            }else{// データベースに未保存だったら
                weatherDao.getAll()
                weatherDao.getAll().forEach{ info ->
                    var tmp = mutableListOf<String>()
                    preference_name += info.name
                    tmp += info.name
                    tmp += info.weather
                    tmp += info.temp_max.toString()
                    tmp += info.temp_min.toString()
                    mmm += info.name to tmp
                }//foreach end
            }//if end

            //グローバル変数にして、セカンドviewでも渡したキーで対応したい
            val wi = WeatherInfo.getInstance()
            wi.QRResult = preference_name
            wi.GVweather = mmm

            //リストデータをリストビューに表示
            val list = findViewById<ListView>(R.id.list_view)
            list.adapter = ArrayAdapter(
                this@MainActivity,
                android.R.layout.simple_list_item_1,
                preference_name)

            //リストがクリックされたときに遷移
            list.setOnItemClickListener { adapterView, _, i, _ ->
                val city = adapterView.getItemAtPosition(i) as String
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                intent.putExtra("CITY", city)
                startActivity(intent)
            }
        }//lifecycleScope end

    }
}
