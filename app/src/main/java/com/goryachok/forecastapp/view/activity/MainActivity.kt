package com.goryachok.forecastapp.view.activity

import android.app.SearchManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.snackbar.Snackbar
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.view.FragmentsAdapter
import com.goryachok.forecastapp.view.fragment.CurrentFragment
import com.goryachok.forecastapp.view.fragment.HourlyFragment
import com.goryachok.forecastapp.view.fragment.MyFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var isNetworkAvailable = false

    private var searchItem: MenuItem? = null

    private val pagerAdapter by lazy { FragmentsAdapter(supportFragmentManager) }

    private val fusedLocationClient by lazy { FusedLocationProviderClient(this) }


    //TODO Move geolocation and connectivity management to separate class

    private val connectivityManager by lazy { getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }

    private val networkRequest by lazy {
        NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    }

    private val networkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                isNetworkAvailable = true
                CoroutineScope(Main).launch {
                    connectionAvailableSnackbar.show()
                    searchItem?.isVisible = true
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                isNetworkAvailable = true
                CoroutineScope(Main).launch {
                    searchItem?.isVisible = false
                    connectionLostSnackbar.show()
                }
            }
        }
    }

    private val connectionLostSnackbar by lazy {
        Snackbar.make(
            constraintLayout_activityMain,
            "Connection lost",
            Snackbar.LENGTH_SHORT
        )
    }

    private val connectionAvailableSnackbar by lazy {
        Snackbar.make(
            constraintLayout_activityMain,
            "Connection available",
            Snackbar.LENGTH_SHORT
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = ""
        initViewPager()
    }

    override fun onStart() {
        super.onStart()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    //TODO Check if all fragments will receive coordinates
    override fun onResumeFragments() {
        super.onResumeFragments()
        passCoordinates()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val manager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchItem = menu?.findItem(R.id.searchItem)
        searchItem?.isVisible = isNetworkAvailable
        val searchView = searchItem?.actionView as SearchView
        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.apply {
                    clearFocus()
                    setQuery("", false)
                }
                (pagerAdapter.getItem(forecast_viewPager.currentItem) as? MyFragment)?.onSearchRequest(
                    query ?: ""
                )
                searchItem?.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.geoLocationItem -> {
                passCoordinates()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun passCoordinates() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                (pagerAdapter.getItem(forecast_viewPager.currentItem) as? MyFragment)?.onLocationRequest(
                    location
                )
            }
            .addOnFailureListener {
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage(it.message)
                    .setPositiveButton("Close") { dialog, _ ->
                        dialog.dismiss()
                    }
            }
    }

    private fun initViewPager() {
        forecast_viewPager.apply {
            adapter = pagerAdapter.apply {
                addFragments(listOf(CurrentFragment(), HourlyFragment()))
            }
        }
    }
}