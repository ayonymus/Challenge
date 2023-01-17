package com.gabor.challenge.feature.coindetails.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.gabor.challenge.feature.coindetails.presentation.CoinDetailsIntent
import com.gabor.challenge.feature.coindetails.presentation.CoinDetailsViewModel
import com.gabor.challenge.feature.coindetails.presentation.ui.CoinDetailsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoinDetailsRoute(
    viewModel: CoinDetailsViewModel = koinViewModel(),
    id: String?,
    onNavigateBack: () -> Unit,
    onUrlClick: (url: String) -> Unit
) {
    remember { viewModel.handleIntent(CoinDetailsIntent.Fetch(id)) }
    CoinDetailsScreen(
        state = viewModel.coinDetailsFlow.collectAsState(),
        onNavigateBack = onNavigateBack,
        onRefresh = { viewModel.handleIntent(CoinDetailsIntent.Refresh(id)) },
        onUrlClick = onUrlClick
    )
}
