@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package com.gabor.challenge.feature.market.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabor.challenge.R
import com.gabor.challenge.feature.market.domain.entites.Coin
import com.gabor.challenge.feature.market.domain.entites.FiatCurrency
import com.gabor.challenge.feature.market.domain.entites.MarketData
import com.gabor.challenge.feature.market.presentation.MarketUiState
import kotlinx.coroutines.launch


@Composable
fun MarketScreen(
    state: State<MarketUiState>,
    onNavigateToDetails: (id: String) -> Unit,
    onRefresh: () -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(state.value.isLoading, onRefresh)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar =  {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.app_name))
            })
        },
        content = { contentPadding ->

            Box(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
            ) {
                MarketList(state = state.value, onNavigateToDetails = onNavigateToDetails)
                if (state.value.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            if (state.value.hasError) {
                val genericError = stringResource(id = R.string.generic_error)
                val refreshText =  stringResource(id = R.string.refresh)
                scope.launch {
                    val snackbarResult = snackbarHostState.showSnackbar(
                        message = genericError,
                        actionLabel = refreshText,
                    )
                    if (snackbarResult == SnackbarResult.ActionPerformed) {
                        onRefresh()
                    }
                }
            }
        }
    )
}

@Composable
fun MarketList(
    state: MarketUiState,
    onNavigateToDetails: (id: String) -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.padding(4.dp)
    ) {
        if (state.latestData == null) {
            items(10) {
                MarketDataPlaceholderItem()
            }
        } else {
            items(state.latestData) { data ->
                MarketItem(data, onNavigateToDetails)
            }
        }
    }
}

@Preview
@Composable
fun MarketListScreenInitialPreview() {
    val marketSate = MarketUiState(true, null, false)
    val state = mutableStateOf(marketSate)
    MarketScreen(state, { }) { }
}

@Preview
@Composable
fun MarketListScreenWithDataPreview() {
    val marketData = MarketData(
        coin = Coin("BTC", "Bitcoin", "https://s2.coinmarketcap.com/static/img/coins/64x64/2499.png", "bitcoin"),
        fiatCurrency = FiatCurrency("USD"),
        price = 1.132
    )
    val marketSate = MarketUiState(
        isLoading = false,
        latestData = MutableList(10) { marketData },
        hasError = false)
    val state = mutableStateOf(marketSate)
    MarketScreen(state, { }) { }
}

@Preview
@Composable
fun MarketListScreenWithDataRefreshingPreview() {
    val marketData = MarketData(
        coin = Coin("BTC", "Bitcoin", "https://s2.coinmarketcap.com/static/img/coins/64x64/2499.png", "bitcoin"),
        fiatCurrency = FiatCurrency("USD"),
        price = 1.132
    )
    val marketSate = MarketUiState(
        isLoading = true,
        latestData = MutableList(10) { marketData },
        hasError = false)
    val state = mutableStateOf(marketSate)
    MarketScreen(state, { }) { }
}
