package com.goryachok.forecastapp.view.activity

import android.app.SearchManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.view.FragmentsAdapter
import com.goryachok.forecastapp.view.fragment.CurrentFragment
import com.goryachok.forecastapp.view.fragment.DailyFragment
import com.goryachok.forecastapp.view.fragment.HourlyFragment
import com.goryachok.forecastapp.view.fragment.MyFragment
import com.goryachok.forecastapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModelFactory by lazy {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(this@MainActivity) as T
            }
        }
    }

    val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        ).get(MainViewModel::class.java)
    }

    private val connectionLostSnackBar by lazy {
        Snackbar.make(
            constraintLayout_activityMain,
            getString(R.string.lost_connection),
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setBackgroundTint(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.lostSnackBarBackground
                )
            )
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.lostSnackBarTextColor))
        }
    }

    private val pagerAdapter by lazy { FragmentsAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = ""
        initViewPager()
        viewModel.locationProvider.setTask {
            (pagerAdapter.getItem(forecast_viewPager.currentItem) as? MyFragment)?.onLocationRequest(
                it
            )
            viewModel.locationCache = it
            viewModel.requestCache = ""
            viewModel.locationProvider.stop()
        }
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewModel.connectivityListener.start()
            viewModel.connectionStatus.observe(this, Observer {
                if (it) {
                    connectionLostSnackBar.dismiss()
                } else {
                    connectionLostSnackBar.show()
                }
            })
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        passCoordinates()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val manager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.searchItem)
        val searchView = searchItem?.actionView as SearchView
        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return if (viewModel.connectivityListener.isNetworkAvailable) {
                    searchView.apply {
                        clearFocus()
                        setQuery("", false)
                    }
                    (pagerAdapter.getItem(forecast_viewPager.currentItem) as? MyFragment)?.onSearchRequest(
                        query.orEmpty()
                    )
                    searchItem.collapseActionView()
                    query?.let { viewModel.requestCache = it }
                    true
                } else {
                    false
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.geoLocationItem -> {
                if (viewModel.connectivityListener.isNetworkAvailable)
                    passCoordinates()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewModel.connectivityListener.stop()
        }
        viewModel.locationProvider.stop()
    }

    private fun passCoordinates() {
        viewModel.locationProvider.start()
    }

    private fun initViewPager() {
        forecast_viewPager.apply {
            adapter = pagerAdapter.apply {
                addFragments(listOf(CurrentFragment(), HourlyFragment(), DailyFragment()))
            }
        }
    }
}