package com.gabor.challenge.feature.market.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.gabor.challenge.feature.market.presentation.MarketIntent
import com.gabor.challenge.feature.market.presentation.MarketViewModel
import com.gabor.challenge.feature.market.presentation.ui.MarketScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MarketRoute(
    viewModel: MarketViewModel = koinViewModel(),
    onNavigateToDetails: (id: String) -> Unit,
) {
    viewModel.handleIntent(MarketIntent.Fetch)
    MarketScreen(
        state = viewModel.marketFlow.collectAsState(),
        onNavigateToDetails = onNavigateToDetails,
        onRefresh = { viewModel.handleIntent(MarketIntent.Refresh) }
    )
}
