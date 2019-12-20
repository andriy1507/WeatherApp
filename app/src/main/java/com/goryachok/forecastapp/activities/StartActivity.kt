package com.goryachok.forecastapp.activities

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.webkit.GeolocationPermissions
import androidx.appcompat.app.AppCompatActivity
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.services.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val serviceIntent = Intent(this, ApiService::class.java)
        val intent = Intent(this, MainActivity::class.java)
        CoroutineScope(IO).launch {
            startService(serviceIntent)
            withContext(Main){
                delay(5000)
                startActivity(intent)
            }
        }
    }
}
