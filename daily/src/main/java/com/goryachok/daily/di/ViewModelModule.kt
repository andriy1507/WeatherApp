package com.goryachok.daily.di

import androidx.lifecycle.ViewModelProvider
import com.goryachok.daily.DailyFragment
import com.goryachok.daily.DailyViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindDailyViewModelFactory(impl: DailyViewModel.Factory): ViewModelProvider.Factory

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideDailyViewModel(
            fragment: DailyFragment,
            factory: ViewModelProvider.Factory
        ): DailyViewModel {
            return ViewModelProvider(fragment, factory).get(DailyViewModel::class.java)
        }
    }
}