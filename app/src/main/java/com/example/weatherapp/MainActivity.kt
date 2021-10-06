package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var textView : TextView
    private lateinit var button : Button
    val Url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        button = findViewById(R.id.button)
        button.setOnClickListener(){
            requestApi()
        }
    }

    private fun requestApi()
    {
        CoroutineScope(Dispatchers.IO).launch {
            val data = async {
                fetchRandomAdvice()
            }.await()

            if (data.isNotEmpty())
            {

                updateAdviceText(data)
            }
        }
    }

    private fun fetchRandomAdvice():String{
        var response=""
        try {
            response = URL(Url).readText(Charsets.UTF_8)
        }catch (e:Exception)
        {
            println("Error $e")
        }
        return response
    }

    private suspend fun updateAdviceText(data:String){
        withContext(Dispatchers.Main)
        {
            val jsonObj = JSONObject(data)
            val main = jsonObj.getJSONObject("main")
            val sys = jsonObj.getJSONObject("sys")
            val wind = jsonObj.getJSONObject("wind")
            val weather = jsonObj.getJSONArray("weather").getJSONObject(0)


        }
    }
}
