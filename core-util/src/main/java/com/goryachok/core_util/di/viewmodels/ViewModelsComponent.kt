package com.goryachok.core_util.di.viewmodels

import com.goryachok.core.di.ViewModelsProvider
import com.goryachok.core_util.di.utils.UtilsProvider
import dagger.Component

@Component(modules = [ViewModelsModule::class], dependencies = [UtilsProvider::class])
interface ViewModelsComponent : ViewModelsProvider {

    @Component.Builder
    interface Builder {

        fun component(component: UtilsProvider): Builder

        fun build(): ViewModelsComponent
    }
}