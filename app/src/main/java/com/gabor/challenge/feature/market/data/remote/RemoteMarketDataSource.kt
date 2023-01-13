package com.gabor.challenge.feature.market.data.remote

import com.gabor.challenge.feature.market.data.repository.RemoteDataSource
import com.gabor.challenge.feature.market.domain.entites.FiatCurrency
import com.gabor.challenge.feature.market.domain.entites.MarketData

class RemoteMarketDataSource(
    val api: CoingeckoMarketDataApi
) : RemoteDataSource<FiatCurrency, List<MarketData>> {

    override suspend fun fetch(arg: FiatCurrency): Result<List<MarketData>> {
        return try {
            val response = api.fetchMarketData(arg.symbol)
            val data = response.body()
            if (data != null) {
                Result.success(data.map { it.toMarketData(arg) })
            } else {
                Result.failure(ApiErrorException(response.message(), response.errorBody()?.toString()))
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}

class ApiErrorException(
    message: String? = null,
    val errorBody: String? = null
): Exception(message)
