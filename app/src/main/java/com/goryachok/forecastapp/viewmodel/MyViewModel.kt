package com.goryachok.forecastapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.model.local.Result

abstract class MyViewModel : ViewModel() {

    protected val _errorData: MutableLiveData<Result.Error> = MutableLiveData()
    val errorData: LiveData<Result.Error>
        get() = _errorData

    protected val _loadData: MutableLiveData<Result.Loading> = MutableLiveData()
    val loadData: LiveData<Result.Loading>
        get() = _loadData
}