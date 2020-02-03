package com.goryachok.core_util.di

import com.goryachok.core.UtilsProvider
import dagger.Component

@Component(modules = [ViewModelsModule::class])
interface UtilsComponent : UtilsProvider {

    @Component.Builder
    interface Builder {

        fun build(): UtilsComponent
    }
}