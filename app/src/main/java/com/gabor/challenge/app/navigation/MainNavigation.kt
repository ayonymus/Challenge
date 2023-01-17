package com.gabor.challenge.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gabor.challenge.feature.coindetails.presentation.navigation.CoinDetailsRoute
import com.gabor.challenge.feature.market.presentation.navigation.MarketRoute

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val uriHandler = LocalUriHandler.current

    NavHost(
        navController = navController,
        startDestination = Routes.MARKET
    ) {
            composable(
                Routes.MARKET
            ) {
                MarketRoute(
                    onNavigateToDetails = { id->
                        navController.navigate(Routes.COIN_DETAILS_ARG + id)
                    }
                )
            }
            composable(
                Routes.COIN_DETAILS,
                arguments = listOf(navArgument("id") { type = NavType.StringType})
            ) { backStackEntry ->
                CoinDetailsRoute(
                    id = backStackEntry.arguments?.getString("id"),
                    onNavigateBack = { navController.popBackStack()},
                    onUrlClick = { uri ->
                        uriHandler.openUri(uri)
                    }
                )
            }
    }
}

object Routes {
    const val MARKET = "market"
    const val COIN_DETAILS = "coin_details/{id}"
    const val COIN_DETAILS_ARG = "coin_details/"
}