package com.goryachok.forecastapp.view.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.WeatherApplication
import com.goryachok.forecastapp.base.PagerFragment
import com.goryachok.forecastapp.di.components.DaggerMainComponent
import com.goryachok.forecastapp.view.adapters.ForecastPagerAdapter
import com.goryachok.forecastapp.view.fragment.CurrentWeatherFragment
import com.goryachok.forecastapp.view.fragment.DailyForecastFragment
import com.goryachok.forecastapp.view.fragment.HourlyForecastFragment
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity :
    DaggerAppCompatActivity(), HasAndroidInjector {

    val pages: List<PagerFragment> = listOf(CurrentWeatherFragment(),HourlyForecastFragment(),DailyForecastFragment())

    val adapter: ForecastPagerAdapter by lazy {
        ForecastPagerAdapter(
            supportFragmentManager,
            pages
        )
    }

    companion object {
        private const val TAG = "DaggerDebug"
    }

    @Suppress("UNCHECKED_CAST")
    override fun androidInjector(): AndroidInjector<Any> {
        val component = (applicationContext as WeatherApplication).component
        return DaggerMainComponent.builder().appComponent(component).build() as AndroidInjector<Any>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, MainActivity::class.java.name)
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
                adapter.pages.forEach {
                    it.onSearchRequest(query ?: "")
                }
                searchItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.geoLocationItem) {
            adapter.pages.forEach {
                it.onLocationRequest()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
