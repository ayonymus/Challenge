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
        when (intent) {
            is CoinDetailsIntent.Fetch -> fetch(intent.id)
            is CoinDetailsIntent.Refresh -> refresh(intent.id)
        }
    }

    private fun fetch(id: String?) {
        viewModelScope.launch {
            if (id == null) {
                Timber.e("Navigation error: Coin ID not received!")
                _coinDetailsFlow.update(isLoading = false, hasError = true)
            } else {
                _coinDetailsFlow.update(isLoading = true, hasError = false)
                handleResult(fetchCoinDetailsUseCase(id))
            }
        }
    }

    private fun refresh(id: String?) {
        viewModelScope.launch {
            if (id == null) {
                Timber.e("Navigation error: Coin ID not received!")
                _coinDetailsFlow.update(isLoading = false, hasError = true)
            } else {
                _coinDetailsFlow.update(isLoading = true, hasError = false)
                handleResult(refreshCoinDetailsUseCase(id))
            }
        }
    }

    private suspend fun handleResult(result: Result<CoinDetails?>) {
        result.fold(
            onSuccess = { data ->
                _coinDetailsFlow.update(
                    isLoading = false,
                    latestData = data,
                    hasError = false,
                )
            },
            onFailure = {
                Timber.e(it)
                _coinDetailsFlow.update(
                    isLoading = false,
                    hasError = true
                )
            }
        )
    }
}

private suspend fun MutableStateFlow<CoinDetailsUiState>.update(
    isLoading: Boolean? = null,
    latestData: CoinDetails? = null,
    hasError: Boolean? = null
) {
    this.value.let { original ->
        val newState = CoinDetailsUiState(
            isLoading = isLoading ?: original.isLoading,
            latestData = latestData ?: original.latestData,
            hasError = hasError ?: original.hasError
        )
        this.emit(newState)
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
