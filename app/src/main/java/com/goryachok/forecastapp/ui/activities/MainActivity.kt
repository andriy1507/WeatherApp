package com.goryachok.forecastapp.ui.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.WeatherApplication
import com.goryachok.forecastapp.ui.adapters.ForecastPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

//        val adapter =
//            ForecastPagerAdapter(supportFragmentManager)
//        forecast_viewPager.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search_menu, menu)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.searchItem)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.apply {
                    clearFocus()
                    setQuery("", false)
                }
                searchItem.collapseActionView()
//                WeatherApplication.repository.getSearchedData(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.geoLocationItem) {
            //          WeatherApplication.repository.initializeData()
        }
        return super.onOptionsItemSelected(item)
    }
}
