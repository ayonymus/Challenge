@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package com.gabor.challenge.feature.coindetails.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gabor.challenge.R
import com.gabor.challenge.feature.coindetails.domain.CoinDetails
import com.gabor.challenge.feature.coindetails.presentation.CoinDetailsUiState
import kotlinx.coroutines.launch


@Composable
fun CoinDetailsScreen(
    state: State<CoinDetailsUiState>,
    onNavigateBack: () -> Unit,
    onRefresh: () -> Unit,
    onUrlClick: (url: String) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(state.value.isLoading, onRefresh)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar =  { appBar(onNavigateBack) },
        content = { contentPadding ->
            Box(
                modifier = Modifier
                    .padding(contentPadding)
                    .pullRefresh(pullRefreshState)
            ) {
                val data = state.value
                if (data.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                if (data.latestData == null) {
                    CoinDetailsLoading()
                } else {
                    DisplayCoinDetails(data.latestData, onUrlClick)
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
fun appBar(onNavigateBack: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.coin_details)) },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back icon")
            }
        }
    )
}

@Preview
@Composable
fun CoinDetailsScreenInitialPreview() {
    val state = mutableStateOf(CoinDetailsUiState(true, null, false))
    CoinDetailsScreen(state, { }, { }) { }
}

@Preview
@Composable
fun CoinDetailsScreenDataPreview() {
    val data = CoinDetails(
        id = "bitcoin",
        symbol = "BTC",
        name = "Bitcoin",
        image = "https://bitcoin-icon.com/png",
        homepage = "https://bitcoin.org",
        description =  "Bitcoin is the first successful internet money based on peer-to-peer technology; whereby no central bank or authority is involved in the transaction and production of the Bitcoin currency. It was created by an anonymous individual/group under the name, Satoshi Nakamoto. The source code is available publicly as an open source project, anybody can look at it and be part of the developmental process.\r\n\r\nBitcoin is changing the way we see money as we speak. The idea was to produce a means of exchange, independent of any central authority, that could be transferred electronically in a secure, verifiable and immutable way. It is a decentralized peer-to-peer internet currency making mobile payment easy, very low transaction fees, protects your identity, and it works anywhere all the time with no central authority and banks.\r\n\r\nBitcoin is designed to have only 21 million BTC ever created, thus making it a deflationary currency. Bitcoin uses the <a href=\"https://www.coingecko.com/en?hashing_algorithm=SHA-256\">SHA-256</a> hashing algorithm with an average transaction confirmation time of 10 minutes. Miners today are mining Bitcoin using ASIC chip dedicated to only mining Bitcoin, and the hash rate has shot up to peta hashes.\r\n\r\nBeing the first successful online cryptography currency, Bitcoin has inspired other alternative currencies such as <a href=\"https://www.coingecko.com/en/coins/litecoin\">Litecoin</a>, <a href=\"https://www.coingecko.com/en/coins/peercoin\">Peercoin</a>, <a href=\"https://www.coingecko.com/en/coins/primecoin\">Primecoin</a>, and so on.\r\n\r\nThe cryptocurrency then took off with the innovation of the turing-complete smart contract by <a href=\"https://www.coingecko.com/en/coins/ethereum\">Ethereum</a> which led to the development of other amazing projects such as <a href=\"https://www.coingecko.com/en/coins/eos\">EOS</a>, <a href=\"https://www.coingecko.com/en/coins/tron\">Tron</a>, and even crypto-collectibles such as <a href=\"https://www.coingecko.com/buzz/ethereum-still-king-dapps-cryptokitties-need-1-billion-on-eos\">CryptoKitties</a>.",
        lastUpdated = ""
    )
    val state = mutableStateOf(CoinDetailsUiState(false, data, false))
    CoinDetailsScreen(state, { }, { }) { }
}

