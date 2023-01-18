package com.gabor.challenge.feature.market.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabor.challenge.feature.market.domain.MarketData
import com.gabor.challenge.feature.market.usecase.FetchMarketDataUseCase
import com.gabor.challenge.feature.market.usecase.RefreshMarketDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MarketViewModel(
    private val fetchMarketDataUseCase: FetchMarketDataUseCase,
    private val refreshMarketDataUseCase: RefreshMarketDataUseCase
) : ViewModel() {

    private val _marketFlow = MutableStateFlow(MarketUiState(true,null, false))
    val marketFlow: StateFlow<MarketUiState> = _marketFlow

    fun handleIntent(intent: MarketIntent) {
        when (intent) {
            is MarketIntent.Fetch -> fetch()
            is MarketIntent.Refresh -> refresh()
        }
    }

    private fun fetch() {
        viewModelScope.launch {
            _marketFlow.update(isLoading = true, hasError = false)
            handleResult(fetchMarketDataUseCase())
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _marketFlow.update(isLoading = true, hasError = false)
            handleResult(refreshMarketDataUseCase())
        }
    }

    private suspend fun handleResult(result: Result<List<MarketData>>) {
        result.fold(
            onSuccess = { data ->
                _marketFlow.update(
                    isLoading = false,
                    latestData = data,
                    hasError = false,
                )
            },
            onFailure = {
                Timber.e(it)
                _marketFlow.update(
                    isLoading = false,
                    hasError = true
                )
            }
        )
    }
}

private suspend fun MutableStateFlow<MarketUiState>.update(
    isLoading: Boolean? = null,
    latestData: List<MarketData>? = null,
    hasError: Boolean? = null
) {
    this.value.let { original ->
        val newState = MarketUiState(
            isLoading = isLoading ?: original.isLoading,
            latestData = latestData ?: original.latestData,
            hasError = hasError ?: original.hasError
        )
        this.emit(newState)
    }
}

data class MarketUiState(
    val isLoading: Boolean,
    val latestData: List<MarketData>?,
    val hasError: Boolean
)

sealed class MarketIntent {
    object Fetch : MarketIntent()
    object Refresh : MarketIntent()
}