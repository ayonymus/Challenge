@file:OptIn(ExperimentalMaterial3Api::class)

package com.gabor.challenge.feature.market.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabor.challenge.R
import com.gabor.challenge.feature.market.domain.entites.Coin
import com.gabor.challenge.feature.market.domain.entites.FiatCurrency
import com.gabor.challenge.feature.market.domain.entites.MarketData
import com.gabor.challenge.feature.market.presentation.MarketUiState


@Composable
fun MarketScreen(state: State<MarketUiState>) {

    Scaffold(
        topBar =  { appBar() },
        content = { contentPadding ->
            Column(
                modifier = Modifier.padding(contentPadding)
            ) {

                if (state.value.errors != null) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Color.Red)
                            .clip(RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center


                    ) {
                        Text(
                            modifier = Modifier,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            text = "Change error text"
                        )

                    }

                }
                MarketList(state = state.value)
            }
        }
    )
}

@Composable
fun appBar() {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
    )
}

@Composable
fun MarketList(
    state: MarketUiState,
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
                MarketItem(data)
            }
        }
    }
}

@Preview
@Composable
fun MarketListScreenInitialPreview() {
    val marketSate = MarketUiState(true, null, null)
    val state = mutableStateOf(marketSate)
    MarketScreen(state)
}

@Preview
@Composable
fun MarketListScreenWithDataPreview() {
    val marketData = MarketData(
        coin = Coin("BTC", "Bitcoin", "https://s2.coinmarketcap.com/static/img/coins/64x64/2499.png"),
        fiatCurrency = FiatCurrency("USD"),
        price = 1.132
    )
    val marketSate = MarketUiState(
        isLoading = true,
        latestData = listOf(marketData, marketData),
        errors = null)
    val state = mutableStateOf(marketSate)
    MarketScreen(state)
}
