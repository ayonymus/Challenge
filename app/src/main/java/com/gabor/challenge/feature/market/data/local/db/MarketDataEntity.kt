package com.gabor.challenge.feature.market.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

const val MARKET_TABLE = "MARKET_TABLE"

@Entity(tableName = MARKET_TABLE)
data class MarketDataEntity(
    @PrimaryKey(autoGenerate = true)val _id: Long,
    val symbol: String,
    val name: String,
    val image: String,
    val fiat: String,
    val price: Double
)
