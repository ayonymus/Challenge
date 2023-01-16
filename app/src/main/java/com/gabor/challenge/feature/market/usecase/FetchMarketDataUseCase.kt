package com.gabor.challenge.feature.market.usecase

import com.gabor.challenge.feature.market.domain.entites.FiatCurrency
import com.gabor.challenge.feature.market.domain.entites.MarketData
import com.gabor.challenge.core.repository.Repository
import com.gabor.challenge.feature.settings.domain.PreferencesProvider

class FetchMarketDataUseCase(
    private val repository: Repository<FiatCurrency, List<MarketData>>,
    private val preferencesProvider: PreferencesProvider
) {

    suspend operator fun invoke(): Result<List<MarketData>> {
        val currency = preferencesProvider.getPreferences().selectedFiatCurrency
        val result = repository.fetchData(currency, false)
        if (result.getOrNull()?.isEmpty() == true) {
            return repository.fetchData(currency, true)
        } else {
            return result
        }
    }
}
