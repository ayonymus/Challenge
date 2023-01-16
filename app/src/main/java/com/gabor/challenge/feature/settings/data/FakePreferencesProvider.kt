package com.gabor.challenge.feature.settings.data

import com.gabor.challenge.feature.market.domain.entites.FiatCurrency
import com.gabor.challenge.feature.settings.domain.Preferences
import com.gabor.challenge.feature.settings.domain.PreferencesProvider

class FakePreferencesProvider: PreferencesProvider {

    override fun getPreferences() = Preferences(
        selectedFiatCurrency = FiatCurrency("USD")
    )
}
