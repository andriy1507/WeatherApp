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
import com.goryachok.forecastapp.view.fragment.MyFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val fragments = listOf<MyFragment>(CurrentFragment())

    private val adapter: FragmentsAdapter by lazy {
        FragmentsAdapter(supportFragmentManager, fragments)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forecast_viewPager.adapter = adapter
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
                adapter.pages.forEach {
                    it.onSearchRequest(query ?: return false)
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
                adapter.pages.forEach { fragment -> fragment.onLocationRequest() }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}