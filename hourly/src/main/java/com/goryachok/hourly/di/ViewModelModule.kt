package com.goryachok.hourly.di

import androidx.lifecycle.ViewModelProvider
import com.goryachok.hourly.HourlyFragment
import com.goryachok.hourly.HourlyViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindHourlyViewModelFactory(impl: HourlyViewModel.Factory): ViewModelProvider.Factory

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideHourlyViewModel(
            fragment: HourlyFragment,
            factory: ViewModelProvider.Factory
        ): HourlyViewModel {
            return ViewModelProvider(fragment, factory).get(HourlyViewModel::class.java)
        }
    }
}