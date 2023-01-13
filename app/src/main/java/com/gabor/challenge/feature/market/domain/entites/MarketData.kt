package com.gabor.challenge.feature.market.domain.entites

data class MarketData(
    val coin: Coin,
    val fiatCurrency: FiatCurrency,
    val price: Double
)
