package com.gabor.challenge.feature.coindetails.data.local

import com.gabor.challenge.core.repository.LocalDataSource
import com.gabor.challenge.database.coindetails.CoinDetailsDao
import com.gabor.challenge.database.coindetails.CoinDetailsEntity
import com.gabor.challenge.feature.coindetails.domain.CoinDetails

class LocalCoinDetailsDataSource(
    private val coinDetailsDao: CoinDetailsDao
): LocalDataSource<String, CoinDetails?> {

    override suspend fun fetch(arg: String): Result<CoinDetails?> {
        return Result.success(coinDetailsDao.getCoinDetails(arg)?.toDomain())
    }

    override suspend fun update(arg: String, data: CoinDetails?) {
        data?.let {
            coinDetailsDao.clear()
            coinDetailsDao.insertCoinDetails(data.toDatabaseEntity())
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