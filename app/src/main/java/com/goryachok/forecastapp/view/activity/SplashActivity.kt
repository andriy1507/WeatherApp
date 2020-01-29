package com.goryachok.forecastapp.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.viewmodel.SplashViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(SplashViewModel::class.java)
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 101
        private const val LOCATION_REQUEST_CODE = 102

    }

    private val mainActivityIntent by lazy {
        Intent(this, MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_CLEAR_TASK }
    }

    private val enableLocationDialog by lazy {
        AlertDialog.Builder(this)
            .setMessage(R.string.unavailable_location_message)
            .setPositiveButton(R.string.ok_button) { _, _ ->
                startActivityForResult(
                    Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                    LOCATION_REQUEST_CODE
                )
            }
            .setNegativeButton(R.string.no_button) { dialog, _ ->
                defaultLocationDialog.show()
                dialog.dismiss()
            }.create()
    }

    @Suppress("NewApi")
    private val locationUsageDialog: AlertDialog by lazy {
        AlertDialog.Builder(this).setTitle(R.string.info_title)
            .setMessage(R.string.location_usage_text)
            .setPositiveButton(getString(R.string.ok_button)) { dialog, _ ->
                dialog.dismiss()
            }.setOnDismissListener {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_CODE
                )
            }.create()
    }

    private val defaultLocationDialog by lazy {
        AlertDialog.Builder(this).setTitle(R.string.info_title)
            .setMessage(R.string.default_location_message)
            .setPositiveButton(getString(R.string.ok_button)) { dialog, _ ->
                dialog.dismiss()
            }.setOnDismissListener {
                viewModel.locationProvider.start()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.startData.observe(this, Observer {
            if (it == true) {
                startActivity(mainActivityIntent)
            }
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !viewModel.locationProvider.permissionGranted()) {
            locationUsageDialog.show()
        } else {
            if (viewModel.locationProvider.isLocationEnabled()) {
                viewModel.locationProvider.start()
                viewModel.startTime = System.currentTimeMillis()
            } else {
                enableLocationDialog.show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                if (viewModel.locationProvider.isLocationEnabled()) {
                    viewModel.locationProvider.start()
                    viewModel.startTime = System.currentTimeMillis()
                } else {
                    enableLocationDialog.show()
                }
            } else {
                defaultLocationDialog.show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        enableLocationDialog.dismiss()
        if (requestCode == LOCATION_REQUEST_CODE && viewModel.locationProvider.isLocationEnabled()) {
            viewModel.locationProvider.start()
            viewModel.startTime = System.currentTimeMillis()
        } else {
            defaultLocationDialog.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.locationProvider.stop()
    }
}