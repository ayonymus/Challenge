package com.gabor.challenge.core.preferences.data

import com.gabor.challenge.feature.market.domain.FiatCurrency
import com.gabor.challenge.core.preferences.domain.Preferences
import com.gabor.challenge.core.preferences.domain.PreferencesProvider

class FakePreferencesProvider: PreferencesProvider {

    override fun getPreferences() = Preferences(
        selectedFiatCurrency = FiatCurrency("USD")
    )
}
