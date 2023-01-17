package com.gabor.challenge.feature.coindetails.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.gabor.challenge.feature.coindetails.presentation.CoinDetailsIntent
import com.gabor.challenge.feature.coindetails.presentation.CoinDetailsViewModel
import com.gabor.challenge.feature.coindetails.presentation.ui.CoinDetailsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoinDetailsRoute(
    viewModel: CoinDetailsViewModel = koinViewModel(),
    id: String?,
    onNavigateBack: () -> Unit
) {
    viewModel.handleIntent(CoinDetailsIntent.Fetch(id))
    CoinDetailsScreen(
        state = viewModel.coinDetailsFlow.collectAsState(),
        onNavigateBack = onNavigateBack
    )
}

/*
@Composable
fun MarketRoute(
    viewModel: MarketViewModel = koinViewModel(),
    onNavigateToDetails: (id: String) -> Unit
) {
    viewModel.handleIntent(MarketIntent.Fetch)
    MarketScreen(
        state = viewModel.marketFlow.collectAsState(),
        onNavigateToDetails = onNavigateToDetails
    )
}

 */