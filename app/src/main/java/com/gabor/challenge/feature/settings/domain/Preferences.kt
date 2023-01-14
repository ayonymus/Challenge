package com.gabor.challenge.feature.settings.domain

import com.gabor.challenge.feature.market.domain.entites.FiatCurrency

data class Preferences(
    val selectedFiatCurrency: FiatCurrency
)