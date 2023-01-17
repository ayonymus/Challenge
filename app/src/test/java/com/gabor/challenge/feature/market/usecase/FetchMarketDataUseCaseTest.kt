package com.gabor.challenge.feature.market.usecase

import com.gabor.challenge.feature.market.domain.FiatCurrency
import com.gabor.challenge.feature.market.domain.MarketData
import com.gabor.challenge.core.repository.Repository
import com.gabor.challenge.core.preferences.domain.Preferences
import com.gabor.challenge.core.preferences.domain.PreferencesProvider
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

private val PREFERENCES = Preferences(FiatCurrency("USD"))

class FetchMarketDataUseCaseTest {

    private val repository: Repository<FiatCurrency, List<MarketData>> = mock()
    private val preferencesProvider: PreferencesProvider = mock()
    private val emptyMarketData: List<MarketData> = emptyList()
    private val marketData: List<MarketData> = mock()

    private lateinit var useCase: FetchMarketDataUseCase

    @Before
    fun setUp() = runBlocking {
        whenever(preferencesProvider.getPreferences()).thenReturn(PREFERENCES)
        whenever(repository.fetchData(PREFERENCES.selectedFiatCurrency, false)).thenReturn(Result.success(marketData))
        whenever(repository.fetchData(PREFERENCES.selectedFiatCurrency, true)).thenReturn(Result.success(marketData))

        whenever(marketData.isEmpty()).thenReturn(false)
        useCase = FetchMarketDataUseCase(repository, preferencesProvider)
    }

    @Test
    fun `GIVEN local data present WHEN usecase invoked THEN fetch stored data`() = runTest {
        val result = useCase()

        assertEquals(Result.success(marketData), result)
    }

    @Test
    fun `GIVEN no local data WHEN usecase invoked THEN try to refresh data`() = runTest {
        whenever(repository.fetchData(PREFERENCES.selectedFiatCurrency, false)).thenReturn(Result.success(emptyMarketData))

        val result = useCase()

        assertEquals(Result.success(marketData), result)
    }

}
