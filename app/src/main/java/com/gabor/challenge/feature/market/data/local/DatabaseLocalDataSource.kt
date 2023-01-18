package com.gabor.challenge.feature.market.data.local

import com.gabor.challenge.database.market.MarketDataDao
import com.gabor.challenge.database.market.toMarketData
import com.gabor.challenge.database.market.toMarketDataEntity
import com.gabor.challenge.core.repository.LocalDataSource
import com.gabor.challenge.feature.market.domain.FiatCurrency
import com.gabor.challenge.feature.market.domain.MarketData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseLocalDataSource(
    private val marketDataDao: MarketDataDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): LocalDataSource<FiatCurrency, List<MarketData>> {

    override suspend fun fetch(currency: FiatCurrency): Result<List<MarketData>> = withContext(dispatcher) {
        return@withContext Result.success(marketDataDao.getMarketData().map { it.toMarketData() })
    }

    override suspend fun update(arg: FiatCurrency, data: List<MarketData>) {
        withContext(dispatcher) {
            marketDataDao.clear()
            marketDataDao.insertAll(data.map { it.toMarketDataEntity() })
        }
    }
}
