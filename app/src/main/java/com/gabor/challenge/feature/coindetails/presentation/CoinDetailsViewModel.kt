package com.gabor.challenge.feature.coindetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabor.challenge.feature.coindetails.domain.CoinDetails
import com.gabor.challenge.feature.coindetails.usecase.FetchCoinDetailsUseCase
import com.gabor.challenge.feature.coindetails.usecase.RefreshCoinDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber


class CoinDetailsViewModel(
    private val fetchCoinDetailsUseCase: FetchCoinDetailsUseCase,
    private val refreshCoinDetailsUseCase: RefreshCoinDetailsUseCase
) : ViewModel() {

    private val _coinDetailsFlow = MutableStateFlow(CoinDetailsUiState(true,null, null))
    val coinDetailsFlow: StateFlow<CoinDetailsUiState> = _coinDetailsFlow

    fun handleIntent(intent: CoinDetailsIntent) {
        val newState: CoinDetailsUiState = when (intent) {
            is CoinDetailsIntent.Fetch -> fetch(intent.id)
            is CoinDetailsIntent.Refresh -> refresh(intent.id)
        }
        viewModelScope.launch {
            _coinDetailsFlow.emit(newState)
        }
    }

    private fun fetch(id: String): CoinDetailsUiState {
        viewModelScope.launch {
            handleResult(fetchCoinDetailsUseCase(id))
        }
        return _coinDetailsFlow.value.copy(isLoading = true)
    }

    private fun refresh(id: String): CoinDetailsUiState {
        viewModelScope.launch {
            handleResult(refreshCoinDetailsUseCase(id))
        }
        return _coinDetailsFlow.value.copy(isLoading = true)
    }

    private suspend fun handleResult(result: Result<CoinDetails?>) {
        result.fold(
            onSuccess = { data ->
                _coinDetailsFlow.emit(
                    _coinDetailsFlow.value.copy(
                        isLoading = false,
                        latestData = data,
                        error = null,
                    )
                )
            },
            onFailure = {
                Timber.e(it)
                _coinDetailsFlow.emit(
                    _coinDetailsFlow.value.copy(
                        isLoading = false,
                        error = "There was an error getting required data", // TODO
                    )
                )
            }
        )
    }
}

data class CoinDetailsUiState(
    val isLoading: Boolean,
    val latestData: CoinDetails?,
    val error: String?
)

sealed class CoinDetailsIntent {
    data class Fetch(val id: String) : CoinDetailsIntent()
    data class Refresh(val id: String) : CoinDetailsIntent()
}