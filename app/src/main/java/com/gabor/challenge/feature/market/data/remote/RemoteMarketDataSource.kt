package com.gabor.challenge.feature.market.data.remote

import com.gabor.challenge.core.exception.RemoteApiErrorException
import com.gabor.challenge.core.repository.RemoteDataSource
import com.gabor.challenge.feature.market.domain.FiatCurrency
import com.gabor.challenge.feature.market.domain.MarketData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteMarketDataSource(
    private val api: CoingeckoMarketDataApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RemoteDataSource<FiatCurrency, List<MarketData>> {

    override suspend fun fetch(arg: FiatCurrency): Result<List<MarketData>> = withContext(dispatcher) {
        return@withContext try {
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
