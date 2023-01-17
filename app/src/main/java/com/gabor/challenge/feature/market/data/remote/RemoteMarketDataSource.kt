package com.gabor.challenge.feature.market.data.remote

import com.gabor.challenge.core.exception.RemoteApiErrorException
import com.gabor.challenge.core.repository.RemoteDataSource
import com.gabor.challenge.feature.market.domain.FiatCurrency
import com.gabor.challenge.feature.market.domain.MarketData

class RemoteMarketDataSource(
    private val api: CoingeckoMarketDataApi
) : RemoteDataSource<FiatCurrency, List<MarketData>> {

    override suspend fun fetch(arg: FiatCurrency): Result<List<MarketData>> {
        return try {
            val response = api.fetchMarketData(arg.currencyName)
            val data = response.body()
            if (data != null) {
                Result.success(data.map { it.toMarketData(arg) })
            } else {
                Result.failure(RemoteApiErrorException(response.message(), response.code(), response.errorBody()?.toString()))
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
