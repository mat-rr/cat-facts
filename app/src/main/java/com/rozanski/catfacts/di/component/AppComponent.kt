package com.rozanski.catfacts.di.component

import android.app.Application
import com.rozanski.catfacts.App
import com.rozanski.catfacts.di.module.ActivityModule
import com.rozanski.catfacts.di.module.ApiModule
import com.rozanski.catfacts.di.module.FragmentModule
import com.rozanski.catfacts.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        ActivityModule::class,
        FragmentModule::class,
        ApiModule::class,
        AndroidInjectionModule::class,
        ViewModelModule::class]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}