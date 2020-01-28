package com.goryachok.forecastapp.view.fragment

import android.app.AlertDialog
import android.location.Location
import androidx.fragment.app.Fragment
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.view.activity.MainActivity
import com.goryachok.forecastapp.viewmodel.MyViewModel

abstract class MyFragment(layout: Int) : Fragment(layout) {

    abstract val viewModel: MyViewModel

    abstract fun onSearchRequest(request: String)

    abstract fun onLocationRequest(loc: Location?)

    override fun onResume() {
        super.onResume()
        val mainViewModel = activity?.let { (it as MainActivity).viewModel }
        if (!mainViewModel?.requestCache.isNullOrEmpty()) {
            onSearchRequest(mainViewModel?.requestCache.orEmpty())
        } else {
            onLocationRequest(mainViewModel?.locationCache)
        }
    }

    protected val errorDialog: AlertDialog by lazy {
        AlertDialog.Builder(this.requireContext()).setTitle(getString(R.string.error))
            .setPositiveButton(R.string.close_button) { dialog, _ -> dialog.dismiss() }.create()
    }
}
