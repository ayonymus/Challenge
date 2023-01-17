package com.gabor.challenge.feature.coindetails.data.remote

import com.gabor.challenge.core.repository.RemoteDataSource
import com.gabor.challenge.core.exception.RemoteApiErrorException
import com.gabor.challenge.feature.coindetails.domain.CoinDetails

class RemoteCoinDetailsSource(
    private val api: CoingeckoCoinDetailsApi
) : RemoteDataSource<String, CoinDetails?> {

    override suspend fun fetch(id: String): Result<CoinDetails?> {
        return try {
            val response = api.fetchMarketData(id)
            val data = response.body()

            if (data != null) {
                Result.success(data.toCoinDetails())
            } else {
                Result.failure(RemoteApiErrorException(response.message(), response.code(), response.errorBody()?.toString()))
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
