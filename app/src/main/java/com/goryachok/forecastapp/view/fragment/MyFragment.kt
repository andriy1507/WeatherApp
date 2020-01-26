package com.goryachok.forecastapp.view.fragment

import android.app.AlertDialog
import android.location.Location
import androidx.fragment.app.Fragment
import com.goryachok.forecastapp.view.activity.MainActivity
import com.goryachok.forecastapp.viewmodel.MyViewModel

abstract class MyFragment(layout: Int) : Fragment(layout) {

    abstract val viewModel: MyViewModel

    abstract fun onSearchRequest(request: String)

    abstract fun onLocationRequest(loc: Location?)

    override fun onResume() {
        super.onResume()
        val mainViewModel = activity?.let { (it as MainActivity).viewModel }
        if (mainViewModel?.requestCache != "") {
            onSearchRequest(mainViewModel?.requestCache ?: "")
        } else {
            onLocationRequest(mainViewModel.locationCache)
        }
    }

    protected val errorDialog: AlertDialog by lazy {
        AlertDialog.Builder(this.requireContext()).setTitle("Error")
            .setPositiveButton("Close") { dialog, _ -> dialog.dismiss() }.create()
    }
}
