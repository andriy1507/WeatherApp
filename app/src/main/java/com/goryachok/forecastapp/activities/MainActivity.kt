package com.goryachok.forecastapp.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.google.gson.Gson
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.fragment.ForecastPagerAdapter
import com.goryachok.forecastapp.model.ForecastResponse
import com.goryachok.forecastapp.model.WeatherResponse
import com.goryachok.forecastapp.services.ApiService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var weather: WeatherResponse
        lateinit var forecast: ForecastResponse
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jsonWeather =
            getSharedPreferences(ApiService.preferencesName, Context.MODE_PRIVATE).getString(
                ApiService.weatherPrefs, ""
            )
        val jsonForecast =
            getSharedPreferences(ApiService.preferencesName, Context.MODE_PRIVATE).getString(
                ApiService.forecastPrefs, ""
            )

        weather = Gson().fromJson(
            jsonWeather,
            WeatherResponse::class.java
        )
        forecast = Gson().fromJson(
            jsonForecast,
            ForecastResponse::class.java
        )
        setContentView(R.layout.activity_main)

        supportActionBar?.title = ""

        forecast_viewPager.adapter = ForecastPagerAdapter(supportFragmentManager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.searchItem)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                val intent = Intent(this@MainActivity, ApiService::class.java)
                val request: String = if (query?.get(query.length - 1) == ' ') {
                    query.substringBeforeLast(" ")
                } else {
                    query.toString()
                }
                intent.putExtra("City", request)
                CoroutineScope(IO).launch {
                    startService(intent)
                    withContext(Main) {
                        delay(750)
                        this@MainActivity.finish()
                        this@MainActivity.startActivity(this@MainActivity.intent)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.i("Search", "QueryTextChange")
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.geoLocationItem -> {
                refreshSharedPrefs()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refreshSharedPrefs() {
        val editor = getSharedPreferences(ApiService.preferencesName, Context.MODE_PRIVATE).edit()
        editor.remove(ApiService.forecastPrefs)
        editor.remove(ApiService.weatherPrefs)
        val intent = Intent(this, ApiService::class.java)
        CoroutineScope(IO).launch {
            startService(intent)
            withContext(Main) {
                delay(750)
                this@MainActivity.finish()
                this@MainActivity.startActivity(this@MainActivity.intent)
            }
        }
    }


}
