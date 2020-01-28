package com.goryachok.forecastapp.view.activity

import android.app.SearchManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.goryachok.forecastapp.ConnectivityListener
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
                return MainViewModel() as T
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
            "Connection lost",
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

    private val connectivityListener: ConnectivityListener by lazy {
        object : ConnectivityListener(this) {
            override fun onConnectionLost() {
                connectionLostSnackBar.show()
            }

            override fun onConnectionAvailable() {
                connectionLostSnackBar.dismiss()
            }
        }
    }

    private val pagerAdapter by lazy { FragmentsAdapter(supportFragmentManager) }

    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = ""
        initViewPager()
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityListener.start()
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
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
                return if (connectivityListener.isNetworkAvailable) {
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
                if (connectivityListener.isNetworkAvailable)
                    passCoordinates()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityListener.stop()
        }
    }

    private fun passCoordinates() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                (pagerAdapter.getItem(forecast_viewPager.currentItem) as? MyFragment)?.onLocationRequest(
                    location
                )
                viewModel.locationCache = location
                viewModel.requestCache = ""
            }
            .addOnFailureListener {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.error))
                    .setMessage(it.message)
                    .setPositiveButton(getString(R.string.close_button)) { dialog, _ ->
                        dialog.dismiss()
                    }
            }
    }

    private fun initViewPager() {
        forecast_viewPager.apply {
            adapter = pagerAdapter.apply {
                addFragments(listOf(CurrentFragment(), HourlyFragment(), DailyFragment()))
            }
        }
    }
}