package com.goryachok.forecastapp.di.viewmodel

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass


@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@MapKey
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ViewModelKey(val value: KClass<out ViewModel>)