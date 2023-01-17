@file:OptIn(ExperimentalMaterial3Api::class)

package com.gabor.challenge.feature.market.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.gabor.challenge.feature.market.domain.Coin
import com.gabor.challenge.feature.market.domain.FiatCurrency
import com.gabor.challenge.feature.market.domain.MarketData


@Composable
fun MarketItem(
    marketData: MarketData,
    onNavigateToDetails: (id: String) -> Unit
) {
    ElevatedCard(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(4.dp),
        onClick = { onNavigateToDetails(marketData.coin.id) }
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            val (icon, tokenName, symbol, price) = createRefs()

            Image(
                painter = rememberAsyncImagePainter(marketData.coin.image),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
                    .constrainAs(icon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
            )

            Text(
                text = marketData.coin.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
                    .constrainAs(tokenName) {
                        start.linkTo(icon.end)
                        top.linkTo(icon.top)
                }
            )

            Text(
                text = marketData.coin.symbol,
                color = Color.Gray,
                modifier = Modifier
                    .padding(8.dp)
                    .constrainAs(symbol) {
                        start.linkTo(icon.end)
                        bottom.linkTo(icon.bottom)
                    }
            )

            Text(
                text = "${marketData.fiatCurrency.currencyName} ${marketData.price}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
                    .constrainAs(price) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
            )

        }
    }
}

@Preview
@Composable
fun MarketItemPreview() {
    val marketData = MarketData(
        coin = Coin("CHSB", "SwissBorg", "https://s2.coinmarketcap.com/static/img/coins/64x64/2499.png", "swissborg"),
        fiatCurrency = FiatCurrency("USD"),
        price = 1.132
    )
    MarketItem(marketData = marketData, onNavigateToDetails = { })
}
