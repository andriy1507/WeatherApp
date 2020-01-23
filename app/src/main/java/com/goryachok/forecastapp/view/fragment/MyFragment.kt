package com.goryachok.forecastapp.view.fragment

import android.app.AlertDialog
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.goryachok.forecastapp.viewmodel.MyViewModel

abstract class MyFragment: Fragment() {

    abstract val viewModel: MyViewModel

    abstract fun onSearchRequest(request: String)

    abstract fun onLocationRequest(loc: Location)

    protected val loadDialog: AlertDialog by lazy {
        AlertDialog.Builder(this.context).setTitle("Wait").setMessage("Data loading").create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorData.observe(viewLifecycleOwner, Observer {
            loadDialog.dismiss()
            AlertDialog.Builder(this.context)
                .setTitle("Error")
                .setMessage(it.exception.message)
                .setPositiveButton("Close") { dialog, _ ->
                    dialog.dismiss()
                }
                .create().show()
        })
        viewModel.loadData.observe(viewLifecycleOwner, Observer {
            loadDialog.show()
        })
    }
}
