package com.goryachok.core_util.di.viewmodels

import com.goryachok.core.viewmodels.MainViewModel
import com.goryachok.core.viewmodels.factories.MainViewModelFactory
import com.goryachok.core_util.viewmodelfactories.MainViewModelFactoryImpl
import com.goryachok.core_util.viewmodels.MainViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(impl: MainViewModelImpl): MainViewModel

    @Binds
    abstract fun bindViewModelFactory(impl: MainViewModelFactoryImpl): MainViewModelFactory
}