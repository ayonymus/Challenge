package com.gabor.challenge.feature.di

import com.gabor.challenge.feature.settings.data.FakePreferencesProvider
import com.gabor.challenge.feature.settings.domain.PreferencesProvider
import org.koin.dsl.module

val preferencesModule = module {
    factory { providePreferences() }
}

fun providePreferences(): PreferencesProvider  {
    return FakePreferencesProvider()
}
