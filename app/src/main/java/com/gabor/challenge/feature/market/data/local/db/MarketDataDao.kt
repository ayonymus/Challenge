package com.gabor.challenge.feature.market.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MarketDataDao {

    @Query("SELECT * FROM $MARKET_TABLE" )
    fun getMarketData(): List<MarketDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<MarketDataEntity>)

    @Query("DELETE FROM $MARKET_TABLE")
    suspend fun clear()
}
