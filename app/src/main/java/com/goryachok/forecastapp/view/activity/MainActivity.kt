package com.goryachok.forecastapp.view.activity

import android.app.SearchManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.view.FragmentsAdapter
import com.goryachok.forecastapp.view.fragment.CurrentFragment
import com.goryachok.forecastapp.view.fragment.HourlyFragment
import com.goryachok.forecastapp.view.fragment.MyFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val pagerAdapter by lazy { FragmentsAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPager()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val manager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.searchItem)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.apply {
                    clearFocus()
                    setQuery("", false)
                }

                for (index in 0 until pagerAdapter.count) {
                    val adapterItem = pagerAdapter.getItem(index) as? MyFragment
                    adapterItem?.onSearchRequest(query ?: return false)
                }

                searchItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.geoLocationItem -> {
                for (index in 0 until pagerAdapter.count) {
                    val adapterItem = pagerAdapter.getItem(index) as? MyFragment
                    adapterItem?.onLocationRequest()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViewPager() {
        forecast_viewPager.apply {
            adapter = pagerAdapter.apply {
                addFragments(listOf(CurrentFragment(), HourlyFragment()))
            }
        }
    }
}