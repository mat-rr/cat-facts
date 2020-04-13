package com.rozanski.catfacts.di.module

import com.rozanski.catfacts.ui.main.FactListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun provideFactListFragment(): FactListFragment
}