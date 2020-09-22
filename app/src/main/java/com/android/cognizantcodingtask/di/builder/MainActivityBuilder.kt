package com.android.cognizantcodingtask.di.builder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.cognizantcodingtask.di.factory.CZViewModelFactory
import com.android.cognizantcodingtask.view.home.MainActivity
import com.android.cognizantcodingtask.di.key.ViewModelKey
import com.android.cognizantcodingtask.view.viewmodel.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class MainActivityBuilder {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: CZViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
}