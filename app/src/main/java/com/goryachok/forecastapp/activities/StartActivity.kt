package com.goryachok.forecastapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.goryachok.forecastapp.services.OpenWeatherService
import com.goryachok.forecastapp.R


class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val intent = Intent(this,
            MainActivity::class.java)

        startService(Intent(this, OpenWeatherService::class.java))
        startActivity(intent)
    }
}
