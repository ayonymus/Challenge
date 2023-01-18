@file:OptIn(ExperimentalCoroutinesApi::class)

package com.gabor.challenge.feature.market.presentation

import com.gabor.challenge.core.exception.RemoteApiErrorException
import com.gabor.challenge.feature.market.domain.MarketData
import com.gabor.challenge.feature.market.usecase.FetchMarketDataUseCase
import com.gabor.challenge.feature.market.usecase.RefreshMarketDataUseCase
import com.gabor.challenge.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class MarketViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val fetchMarketDataUseCase: FetchMarketDataUseCase = mock()
    private val refreshMarketDataUseCase: RefreshMarketDataUseCase = mock()

    private lateinit var viewModel: MarketViewModel

    @Before
    fun setUp() {
        viewModel = MarketViewModel(fetchMarketDataUseCase, refreshMarketDataUseCase)
    }

    @Test
    fun `GIVEN Fetch intent issued WHEN data retrieved THEN emit loading then data`() = runTest {
        val mockList: List<MarketData> = mock()
        val result = Result.success(mockList)
        whenever(fetchMarketDataUseCase.invoke()).thenReturn(result)

        val marketStateFlow = viewModel.marketFlow
        val collectJob = launch(UnconfinedTestDispatcher()) { marketStateFlow.collect() }

        assertEquals(MarketUiState(true, null, false), marketStateFlow.value)

        viewModel.handleIntent(MarketIntent.Fetch)

        assertEquals(MarketUiState(false, mockList, false), marketStateFlow.value)

        collectJob.cancel()
    }

    @Test
    fun `GIVEN Refresh intent issued WHEN data retrieved THEN emit loading then data`() = runTest {
        val mockList: List<MarketData> = mock()
        val result = Result.success(mockList)
        whenever(refreshMarketDataUseCase.invoke()).thenReturn(result)

        val marketStateFlow = viewModel.marketFlow
        val collectJob = launch(UnconfinedTestDispatcher()) { marketStateFlow.collect() }

        assertEquals(MarketUiState(true, null, false), marketStateFlow.value)

        viewModel.handleIntent(MarketIntent.Refresh)

        assertEquals(MarketUiState(false, mockList, false), marketStateFlow.value)

        collectJob.cancel()
    }

    @Test
    fun `GIVEN Fetch intent issued WHEN data errors THEN emit loading then error`() = runTest {
        val mockError: RemoteApiErrorException = mock()
        val result = Result.failure<List<MarketData>>(mockError)
        whenever(refreshMarketDataUseCase.invoke()).thenReturn(result)

        val marketStateFlow = viewModel.marketFlow
        val collectJob = launch(UnconfinedTestDispatcher()) { marketStateFlow.collect() }

        assertEquals(MarketUiState(true, null, false), marketStateFlow.value)

        viewModel.handleIntent(MarketIntent.Refresh)

        assertEquals(MarketUiState(false, null, true), marketStateFlow.value)

        collectJob.cancel()
    }
}