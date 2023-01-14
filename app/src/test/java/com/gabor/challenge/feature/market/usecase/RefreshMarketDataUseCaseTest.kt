package com.gabor.challenge.feature.market.usecase

import com.gabor.challenge.feature.market.domain.entites.FiatCurrency
import com.gabor.challenge.feature.market.domain.entites.MarketData
import com.gabor.challenge.feature.market.domain.repository.Repository
import com.gabor.challenge.feature.settings.domain.Preferences
import com.gabor.challenge.feature.settings.domain.PreferencesProvider
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

private val PREFERENCES = Preferences(FiatCurrency("USD"))

class RefreshMarketDataUseCaseTest {

    private val repository: Repository<FiatCurrency, List<MarketData>> = mock()
    private val preferencesProvider: PreferencesProvider = mock()

    private lateinit var useCase: RefreshMarketDataUseCase

    @Before
    fun setUp() = runBlocking {
        whenever(preferencesProvider.getPreferences()).thenReturn(PREFERENCES)
        whenever(repository.fetchData(PREFERENCES.selectedFiatCurrency, true)).thenReturn(Result.success(listOf()))
        useCase = RefreshMarketDataUseCase(repository, preferencesProvider)
    }

    @Test
    fun `GIVEN preferences WHEN usecase invoked THEN fetch stored data`() = runTest {
        val result = useCase()

        assertEquals(Result.success(listOf<MarketData>()), result)
    }
}
