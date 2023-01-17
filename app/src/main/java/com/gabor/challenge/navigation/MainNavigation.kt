package com.gabor.challenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gabor.challenge.feature.market.presentation.navigation.MarketRoute

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MARKET
    ) {
            composable(Routes.MARKET) { MarketRoute(onNavigateToDetails = { navController.navigate(Routes.COIN_DETAILS) })
            composable(Routes.COIN_DETAILS) { }
        }
    }
}

object Routes {
    const val MARKET = "market"
    const val COIN_DETAILS = "coin_details"
}