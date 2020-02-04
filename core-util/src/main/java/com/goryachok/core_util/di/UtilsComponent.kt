package com.goryachok.core_util.di

import com.goryachok.core.App
import com.goryachok.core.di.UtilsProvider
import dagger.BindsInstance
import dagger.Component

@Component(modules = [UtilsModule::class])
interface UtilsComponent : UtilsProvider {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(app: App): Builder

        fun build(): UtilsComponent
    }

    class Initializer {
        fun init(app: App): UtilsProvider =
            DaggerUtilsComponent.builder().app(app).build()
    }
}