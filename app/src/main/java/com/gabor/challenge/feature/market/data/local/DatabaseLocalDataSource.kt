package com.gabor.challenge.feature.market.data.local

import com.gabor.challenge.feature.market.data.local.db.MarketDataDao
import com.gabor.challenge.feature.market.data.local.db.toMarketData
import com.gabor.challenge.feature.market.data.local.db.toMarketDataEntity
import com.gabor.challenge.core.repository.LocalDataSource
import com.gabor.challenge.feature.market.domain.entites.FiatCurrency
import com.gabor.challenge.feature.market.domain.entites.MarketData

class DatabaseLocalDataSource(
    private val marketDataDao: MarketDataDao
): LocalDataSource<FiatCurrency, List<MarketData>> {

    override suspend fun fetch(currency: FiatCurrency): Result<List<MarketData>> {
        return Result.success(marketDataDao.getMarketData().map { it.toMarketData() })
    }

    override suspend fun update(arg: FiatCurrency, data: List<MarketData>) {
        marketDataDao.clear()
        marketDataDao.insertAll(data.map { it.toMarketDataEntity() })
    }
}
