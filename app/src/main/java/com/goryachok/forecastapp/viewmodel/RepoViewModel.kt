package com.goryachok.forecastapp.viewmodel

import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.repository.Repository

abstract class RepoViewModel : ViewModel() {
    abstract var repository: Repository
}
