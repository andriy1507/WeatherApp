package com.goryachok.forecastapp.view.fragment

import android.app.AlertDialog
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.view.activity.MainActivity
import com.goryachok.forecastapp.viewmodel.MyViewModel
import dagger.android.support.DaggerFragment

abstract class MyFragment(private val layout: Int) : DaggerFragment() {

    abstract val viewModel: MyViewModel

    abstract fun onSearchRequest(request: String)

    abstract fun onLocationRequest(loc: Location?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

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
