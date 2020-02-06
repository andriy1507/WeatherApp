package com.goryachok.main.di

import androidx.lifecycle.ViewModelProvider
import com.goryachok.main.MainActivity
import com.goryachok.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal abstract class ViewModelModule {

    @Binds
    abstract fun bindMainViewModelFactory(impl: MainViewModel.Factory): ViewModelProvider.Factory

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideMainViewModel(
            activity: MainActivity,
            factory: ViewModelProvider.Factory
        ): MainViewModel =
            ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }
}

