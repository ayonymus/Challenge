package com.gabor.challenge.feature.market.domain

data class MarketData(
    val coin: Coin,
    val fiatCurrency: FiatCurrency,
    val price: Double
)
