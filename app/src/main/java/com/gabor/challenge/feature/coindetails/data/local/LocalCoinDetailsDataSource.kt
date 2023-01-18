package com.gabor.challenge.feature.coindetails.data.local

import com.gabor.challenge.core.repository.LocalDataSource
import com.gabor.challenge.database.coindetails.CoinDetailsDao
import com.gabor.challenge.database.coindetails.CoinDetailsEntity
import com.gabor.challenge.feature.coindetails.domain.CoinDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalCoinDetailsDataSource(
    private val coinDetailsDao: CoinDetailsDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): LocalDataSource<String, CoinDetails?> {

    override suspend fun fetch(arg: String): Result<CoinDetails?> = withContext(dispatcher) {
        return@withContext Result.success(coinDetailsDao.getCoinDetails(arg)?.toDomain())
    }

    override suspend fun update(arg: String, data: CoinDetails?) {
        withContext(dispatcher) {
            data?.let {
                coinDetailsDao.insertCoinDetails(data.toDatabaseEntity())
            }
        }
    }
}

fun CoinDetails.toDatabaseEntity() =
    CoinDetailsEntity(
        id = this.id,
        symbol = this.symbol,
        name = this.name,
        image = this.image,
        homepage = this.homepage,
        description = this.description,
        lastUpdated = this.lastUpdated
    )

fun CoinDetailsEntity.toDomain() =
    CoinDetails(
        id = this.id,
        symbol = this.symbol,
        name = this.name,
        image = this.image,
        homepage = this.homepage,
        description = this.description,
        lastUpdated = this.lastUpdated
    )