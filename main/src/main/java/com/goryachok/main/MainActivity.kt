package com.goryachok.main

import android.app.SearchManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.goryachok.core_ui.BaseActivity
import com.goryachok.core_ui.ErrorSnackBar
import com.goryachok.core_ui.base.BaseFragment
import com.goryachok.current.CurrentFragment
import com.goryachok.daily.DailyFragment
import com.goryachok.hourly.HourlyFragment
import com.goryachok.main.di.MainActivityComponent
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    private val connectionLostSnackBar by lazy {
        ErrorSnackBar.Builder(this)
            .setLayout(constraintLayout_activityMain)
            .setMessage(R.string.lost_connection)
            .setDuration(Snackbar.LENGTH_INDEFINITE)
            .setBackgroundColor(R.color.lostSnackBarBackground)
            .setTextColor(R.color.lostSnackBarTextColor)
            .build()
    }

    private val pagerAdapter by lazy { FragmentAdapter(supportFragmentManager) }

    override fun setupDependencies() {
        MainActivityComponent.Initializer().init(this).inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = ""
        initViewPager()
        viewModel.setTaskForLocationProvider {
            (pagerAdapter.getItem(forecast_viewPager.currentItem) as? BaseFragment)?.onLocationRequest(
                it
            )
            with(viewModel) {
                locationCache = it
                requestCache = ""
                stopLocationProvider()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            with(viewModel) {
                startConnectivityListener()
                viewModel.connectionStatus.observe(this@MainActivity, Observer {
                    if (it) {
                        connectionLostSnackBar.dismiss()
                    } else {
                        connectionLostSnackBar.show()
                    }
                })
            }
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
                return if (viewModel.isNetworkAvailable()) {
                    searchView.apply {
                        clearFocus()
                        setQuery("", false)
                    }
                    (pagerAdapter.getItem(forecast_viewPager.currentItem) as? BaseFragment)?.onSearchRequest(
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
                if (viewModel.isNetworkAvailable())
                    passCoordinates()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewModel.stopConnectivityListener()
        }
        viewModel.stopLocationProvider()
    }

    private fun passCoordinates() {
        viewModel.startLocationProvider()
    }

    private fun initViewPager() {
        forecast_viewPager.apply {
            adapter = pagerAdapter.apply {
                setFragments(
                    listOf(
                        CurrentFragment.newInstance(),
                        HourlyFragment.newInstance(),
                        DailyFragment.newInstance()
                    )
                )
            }
        }
    }
}