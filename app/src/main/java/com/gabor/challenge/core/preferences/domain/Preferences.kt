package com.gabor.challenge.core.preferences.domain

import com.gabor.challenge.feature.market.domain.FiatCurrency

data class Preferences(
    val selectedFiatCurrency: FiatCurrency
)