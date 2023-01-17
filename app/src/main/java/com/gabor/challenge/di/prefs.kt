package com.gabor.challenge.di

import com.gabor.challenge.core.preferences.data.FakePreferencesProvider
import com.gabor.challenge.core.preferences.domain.PreferencesProvider
import org.koin.dsl.module

val preferencesModule = module {
    factory { providePreferences() }
}

fun providePreferences(): PreferencesProvider {
    return FakePreferencesProvider()
}
