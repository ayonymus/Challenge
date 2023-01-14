package com.gabor.challenge.feature.market.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabor.challenge.feature.market.domain.entites.MarketData
import com.gabor.challenge.feature.market.usecase.FetchMarketDataUseCase
import com.gabor.challenge.feature.market.usecase.RefreshMarketDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MarketViewModel(
    private val fetchMarketDataUseCase: FetchMarketDataUseCase,
    private val refreshMarketDataUseCase: RefreshMarketDataUseCase
) : ViewModel() {

    private val _marketFlow = MutableStateFlow(MarketUiState(true,null, null))
    val marketFlow: StateFlow<MarketUiState> = _marketFlow

    fun handleIntent(intent: MarketIntent) {
        val newState: MarketUiState = when (intent) {
            is MarketIntent.Fetch -> fetch()
            is MarketIntent.Refresh -> refresh()
        }
        viewModelScope.launch {
            _marketFlow.emit(newState)
        }
    }

    private fun fetch(): MarketUiState {
        viewModelScope.launch {
            handleResult(fetchMarketDataUseCase())
        }
        return _marketFlow.value.copy(isLoading = true)
    }

    private fun refresh(): MarketUiState {
        viewModelScope.launch {
            handleResult(refreshMarketDataUseCase())
        }
        return _marketFlow.value.copy(isLoading = true)
    }

    private suspend fun handleResult(result: Result<List<MarketData>>) {
        result.fold(
            onSuccess = { data ->
                _marketFlow.emit(
                    _marketFlow.value.copy(
                        isLoading = false,
                        latestData = data,
                        errors = null,
                    )
                )
            },
            onFailure = {
                _marketFlow.emit(
                    _marketFlow.value.copy(
                        isLoading = false,
                        errors = "There was an error getting required data", // TODO
                    )
                )
            }
        )
    }
}

data class MarketUiState(
    val isLoading: Boolean,
    val latestData: List<MarketData>?,
    val errors: String?
)

sealed class MarketIntent {
    object Fetch : MarketIntent()
    object Refresh : MarketIntent()
}