package com.gabor.challenge.database.market

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gabor.challenge.feature.market.domain.entites.Coin
import com.gabor.challenge.feature.market.domain.entites.FiatCurrency
import com.gabor.challenge.feature.market.domain.entites.MarketData

const val MARKET_TABLE = "MARKET_TABLE"

@Entity(tableName = MARKET_TABLE)
data class MarketDataEntity(
    @PrimaryKey val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val fiat: String,
    val price: Double
)

fun MarketDataEntity.toMarketData() =
    MarketData(
        coin = Coin(symbol = symbol, name = name, image = image, id = id),
        fiatCurrency = FiatCurrency(fiat),
        price = price
    )

fun MarketData.toMarketDataEntity() =
    MarketDataEntity(
        id = coin.id,
        symbol = coin.symbol,
        name = coin.name,
        image = coin.image,
        fiat = fiatCurrency.symbol,
        price = price
    )
