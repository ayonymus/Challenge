package com.gabor.challenge.feature.market.data.remote

import com.gabor.challenge.feature.market.domain.entites.Coin
import com.gabor.challenge.feature.market.domain.entites.FiatCurrency
import com.gabor.challenge.feature.market.domain.entites.MarketData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoingeckoMarketDataApi {

    @GET("api/v3/coins/markets")
    suspend fun fetchMarketData(@Query("vs_currency", encoded = true) currency: String): Response<List<CoinggeckoMarketData>>

}

data class CoinggeckoMarketData(
    val symbol: String,
    val name: String,
    val id: String,
    val image: String,
    val current_price: Double
)

fun CoinggeckoMarketData.toMarketData(currency: FiatCurrency) =
    MarketData(
        coin = Coin(symbol = symbol, name = name, image = image, id = id),
        fiatCurrency = currency,
        price = current_price
    )
