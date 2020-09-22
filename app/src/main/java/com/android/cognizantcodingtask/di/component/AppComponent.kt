package com.android.cognizantcodingtask.di.component

import com.android.cognizantcodingtask.app.CZApplication
import com.android.cognizantcodingtask.di.builder.MainActivityBuilder
import com.android.cognizantcodingtask.di.module.AppModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, AppModule::class, MainActivityBuilder::class]
)
interface AppComponent : AndroidInjector<CZApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<CZApplication>()
}