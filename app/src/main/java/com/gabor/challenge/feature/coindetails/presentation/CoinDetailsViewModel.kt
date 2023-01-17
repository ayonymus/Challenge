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

    private val _coinDetailsFlow = MutableStateFlow(CoinDetailsUiState(true,null, false))
    val coinDetailsFlow: StateFlow<CoinDetailsUiState> = _coinDetailsFlow

    fun handleIntent(intent: CoinDetailsIntent) {
        Timber.d("Intent: $intent")
        val newState: CoinDetailsUiState = when (intent) {
            is CoinDetailsIntent.Fetch -> fetch(intent.id)
            is CoinDetailsIntent.Refresh -> refresh(intent.id)
        }
        viewModelScope.launch {
            _coinDetailsFlow.emit(newState)
        }
    }

    private fun fetch(id: String?): CoinDetailsUiState {
        return if (id == null) {
            Timber.e("Framework error: Coin ID not received!")
            _coinDetailsFlow.value.copy(isLoading = false, hasError = true)
        } else {
            viewModelScope.launch {
                handleResult(fetchCoinDetailsUseCase(id))
            }
            _coinDetailsFlow.value.copy(isLoading = true, hasError = false)
        }
    }

    private fun refresh(id: String?): CoinDetailsUiState {
        return if (id == null) {
            Timber.e("Framework error: Coin ID not received!")
            _coinDetailsFlow.value.copy(isLoading = false, hasError = true)
        } else {
            viewModelScope.launch {
                handleResult(refreshCoinDetailsUseCase(id))
            }
            _coinDetailsFlow.value.copy(isLoading = true, hasError = false)
        }
    }

    private suspend fun handleResult(result: Result<CoinDetails?>) {
        result.fold(
            onSuccess = { data ->
                _coinDetailsFlow.emit(
                    _coinDetailsFlow.value.copy(
                        isLoading = false,
                        latestData = data,
                        hasError = false,
                    )
                )
            },
            onFailure = {
                Timber.e(it)
                _coinDetailsFlow.emit(
                    _coinDetailsFlow.value.copy(
                        isLoading = false,
                        hasError = true
                    )
                )
            }
        )
    }
}

data class CoinDetailsUiState(
    val isLoading: Boolean,
    val latestData: CoinDetails?,
    val hasError: Boolean
)

sealed class CoinDetailsIntent {
    data class Fetch(val id: String?) : CoinDetailsIntent()
    data class Refresh(val id: String?) : CoinDetailsIntent()
}