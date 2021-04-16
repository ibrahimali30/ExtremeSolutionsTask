package com.ibrahim.orcastask.base.di

import android.app.Application
import com.ibrahim.extremesolutionstask.base.AppAplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = []
)


interface AppComponent {

    fun inject(app: AppAplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }

}