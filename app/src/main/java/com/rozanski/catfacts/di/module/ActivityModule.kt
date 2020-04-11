package com.rozanski.catfacts.di.module

import com.rozanski.catfacts.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract fun provideMainActivity(): MainActivity

}
