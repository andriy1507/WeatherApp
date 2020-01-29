package com.goryachok.forecastapp.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goryachok.forecastapp.di.repository.RepositoryModule
import com.goryachok.forecastapp.di.util.ConnectivityListenerModule
import com.goryachok.forecastapp.di.util.LocationProviderModule
import com.goryachok.forecastapp.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(
    includes = [RepositoryModule::class,
        LocationProviderModule::class,
        ConnectivityListenerModule::class]
)
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CurrentViewModel::class)
    abstract fun bindCurrentViewModel(viewModel: CurrentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HourlyViewModel::class)
    abstract fun bindHourlyViewModel(viewModel: HourlyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DailyViewModel::class)
    abstract fun bindDailyViewModel(viewModel: DailyViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}