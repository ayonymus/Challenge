package com.gabor.challenge.feature.coindetails.usecase

import com.gabor.challenge.core.repository.Repository
import com.gabor.challenge.feature.coindetails.domain.CoinDetails

class FetchCoinDetailsUseCase(
    private val repository: Repository<String, CoinDetails?>
) {

    suspend operator fun invoke(id: String): Result<CoinDetails?> {
        val result = repository.fetchData(id, false)
        return if (result.getOrNull() == null) {
            repository.fetchData(id, true)
        } else {
            result
        }
    }
}
