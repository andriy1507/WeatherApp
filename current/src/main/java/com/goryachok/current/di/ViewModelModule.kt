package com.goryachok.current.di

import androidx.lifecycle.ViewModelProvider
import com.goryachok.current.CurrentFragment
import com.goryachok.current.CurrentViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindCurrentViewModelFactory(impl: CurrentViewModel.Factory): ViewModelProvider.Factory

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideCurrentViewModel(
            fragment: CurrentFragment,
            factory: ViewModelProvider.Factory
        ): CurrentViewModel {
            return ViewModelProvider(fragment, factory).get(CurrentViewModel::class.java)
        }
    }
}