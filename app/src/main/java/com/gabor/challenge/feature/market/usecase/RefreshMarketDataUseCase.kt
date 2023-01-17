package com.gabor.challenge.feature.market.usecase

import com.gabor.challenge.feature.market.domain.FiatCurrency
import com.gabor.challenge.feature.market.domain.MarketData
import com.gabor.challenge.core.repository.Repository
import com.gabor.challenge.core.preferences.domain.PreferencesProvider

class RefreshMarketDataUseCase(
    private val repository: Repository<FiatCurrency, List<MarketData>>,
    private val preferencesProvider: PreferencesProvider
) {

    suspend operator fun invoke(): Result<List<MarketData>> {
        val currency = preferencesProvider.getPreferences().selectedFiatCurrency
        return repository.fetchData(currency, true)
    }
}
