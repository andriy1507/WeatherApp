package com.goryachok.core_util.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelsModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactoryImpl): ViewModelProvider.Factory
}