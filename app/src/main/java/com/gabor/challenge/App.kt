package com.gabor.challenge

import android.app.Application
import com.gabor.challenge.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@App)
            modules(
                appModule,
                networkModule,
                databaseModule,
                marketDataModule,
                marketUseCaseModule,
                marketUiModule,
                coinDetailsModule,
                coinDetailsUseCaseModule,
                coinDetailsUiModule,
                preferencesModule,
            )
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
