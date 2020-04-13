package com.rozanski.catfacts.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rozanski.catfacts.di.key.ViewModelKey
import com.rozanski.catfacts.ui.main.SharedViewModel
import com.rozanski.catfacts.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SharedViewModel::class)
    protected abstract fun bindMainViewModel(sharedViewModel: SharedViewModel): ViewModel
}