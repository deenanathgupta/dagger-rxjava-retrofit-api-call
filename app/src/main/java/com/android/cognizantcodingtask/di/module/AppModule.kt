package com.android.cognizantcodingtask.di.module

import com.android.cognizantcodingtask.network.RestApiClient
import com.android.cognizantcodingtask.network.RestService
import com.android.cognizantcodingtask.utils.NavigationUtils
import com.android.cognizantcodingtask.utils.rxbus.RxBus
import com.android.cognizantcodingtask.utils.rxbus.RxBusImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {
    @Provides
    @Singleton
    fun provideRxBus(): RxBus {
        return RxBusImpl()
    }

    @Provides
    @Singleton
    fun provideAppNavigator(): NavigationUtils {
        return NavigationUtils()
    }

    @Provides
    @Singleton
    fun provideRestService(): RestService {
        val retrofit = RestApiClient.getApiClient().retrofit
        return retrofit.create(RestService::class.java)
    }
}