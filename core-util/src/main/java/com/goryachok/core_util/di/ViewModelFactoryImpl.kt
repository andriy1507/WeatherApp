package com.goryachok.core_util.di

import androidx.lifecycle.ViewModel
import com.goryachok.core.base.viewmodels.ViewModelFactory
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactoryImpl @Inject constructor(
    private val viewModelProviderMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelFactory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelProvider = viewModelProviderMap[modelClass]
            ?: throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        return viewModelProvider.get() as T
    }
}