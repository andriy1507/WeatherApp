package com.goryachok.core_util.di

import com.goryachok.core.ApplicationProvider
import com.goryachok.core.UtilsProvider
import dagger.Component

@Component(modules = [UtilsModule::class], dependencies = [ApplicationProvider::class])
interface UtilsComponent : UtilsProvider {

    @Component.Builder
    interface Builder {

        fun component(provider: ApplicationProvider): Builder

        fun build(): UtilsComponent
    }
}