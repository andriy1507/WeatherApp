package com.goryachok.forecastapp.view.fragment

import android.app.AlertDialog
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.goryachok.forecastapp.viewmodel.MyViewModel

abstract class MyFragment(layout: Int) : Fragment(layout) {

    abstract val viewModel: MyViewModel

    abstract fun onSearchRequest(request: String)

    abstract fun onLocationRequest(loc: Location?)

    protected val loadDialog: AlertDialog by lazy {
        AlertDialog.Builder(this.requireContext()).setTitle("Wait")
            .setMessage("Data loading").create()
    }

    private val errorDialog: AlertDialog by lazy {
        AlertDialog.Builder(this.requireContext()).setTitle("Error")
            .setPositiveButton("Close") { dialog, _ -> dialog.dismiss() }.create()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.errorData.observe(viewLifecycleOwner, Observer { result ->
            this.context?.let { _ ->
                loadDialog.dismiss()
                errorDialog.setMessage(result.exception.message)
                errorDialog.show()
            }
        })
        viewModel.loadData.observe(viewLifecycleOwner, Observer {
            this.context?.let {
                loadDialog.show()
            }
        })
    }
}
