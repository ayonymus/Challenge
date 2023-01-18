@file:OptIn(ExperimentalCoroutinesApi::class)

package com.gabor.challenge.feature.coindetails.presentation

import com.gabor.challenge.core.exception.RemoteApiErrorException
import com.gabor.challenge.feature.coindetails.domain.CoinDetails
import com.gabor.challenge.feature.coindetails.usecase.FetchCoinDetailsUseCase
import com.gabor.challenge.feature.coindetails.usecase.RefreshCoinDetailsUseCase
import com.gabor.challenge.feature.market.domain.MarketData
import com.gabor.challenge.feature.market.presentation.MarketIntent
import com.gabor.challenge.feature.market.presentation.MarketUiState
import com.gabor.challenge.feature.market.presentation.MarketViewModel
import com.gabor.challenge.feature.market.usecase.FetchMarketDataUseCase
import com.gabor.challenge.feature.market.usecase.RefreshMarketDataUseCase
import com.gabor.challenge.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

private val ID = "bitcoin"

class CoinDetailsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val fetchCoinDetailsUseCase: FetchCoinDetailsUseCase = mock()
    private val refreshCoinDetailsUseCase: RefreshCoinDetailsUseCase = mock()

    private lateinit var viewModel: CoinDetailsViewModel

    @Before
    fun setUp() {
        viewModel = CoinDetailsViewModel(fetchCoinDetailsUseCase, refreshCoinDetailsUseCase)
    }

    @Test
    fun `GIVEN Fetch intent issued WHEN data retrieved THEN emit loading then data`() = runTest {
        val mockDetails: CoinDetails = mock()
        val result = Result.success(mockDetails)
        whenever(fetchCoinDetailsUseCase.invoke(ID)).thenReturn(result)

        val coinDetailsFlow = viewModel.coinDetailsFlow
        val collectJob = launch(UnconfinedTestDispatcher()) { coinDetailsFlow.collect() }

        assertEquals(CoinDetailsUiState(true, null, false), coinDetailsFlow.value)

        viewModel.handleIntent(CoinDetailsIntent.Fetch(ID))

        assertEquals(CoinDetailsUiState(false, mockDetails, false), coinDetailsFlow.value)

        collectJob.cancel()
    }

    @Test
    fun `GIVEN Refresh intent issued WHEN data retrieved THEN emit loading then data`() = runTest {
        val mockDetails: CoinDetails = mock()
        val result = Result.success(mockDetails)
        whenever(refreshCoinDetailsUseCase.invoke(ID)).thenReturn(result)

        val coinDetailsFlow = viewModel.coinDetailsFlow
        val collectJob = launch(UnconfinedTestDispatcher()) { coinDetailsFlow.collect() }

        assertEquals(CoinDetailsUiState(true, null, false), coinDetailsFlow.value)

        viewModel.handleIntent(CoinDetailsIntent.Refresh(ID))

        assertEquals(CoinDetailsUiState(false, mockDetails, false), coinDetailsFlow.value)

        collectJob.cancel()
    }

    @Test
    fun `GIVEN Fetch intent issued WHEN data errors THEN emit loading then error`() = runTest {
        val mockError: RemoteApiErrorException = mock()
        val result = Result.failure<CoinDetails>(mockError)
        whenever(refreshCoinDetailsUseCase.invoke(ID)).thenReturn(result)

        val coinDetailsFlow = viewModel.coinDetailsFlow
        val collectJob = launch(UnconfinedTestDispatcher()) { coinDetailsFlow.collect() }

        assertEquals(CoinDetailsUiState(true, null, false), coinDetailsFlow.value)

        viewModel.handleIntent(CoinDetailsIntent.Refresh(ID))

        assertEquals(CoinDetailsUiState(false, null, true), coinDetailsFlow.value)

        collectJob.cancel()
    }

    @Test
    fun `GIVEN Fetch intent issued WHEN no id received THEN emit loading then error`() = runTest {

        val coinDetailsFlow = viewModel.coinDetailsFlow
        val collectJob = launch(UnconfinedTestDispatcher()) { coinDetailsFlow.collect() }

        assertEquals(CoinDetailsUiState(true, null, false), coinDetailsFlow.value)

        viewModel.handleIntent(CoinDetailsIntent.Refresh(null))

        assertEquals(CoinDetailsUiState(false, null, true), coinDetailsFlow.value)

        collectJob.cancel()
    }
}