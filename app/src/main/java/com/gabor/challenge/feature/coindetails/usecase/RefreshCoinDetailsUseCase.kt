package com.gabor.challenge.feature.coindetails.usecase

import com.gabor.challenge.core.repository.Repository
import com.gabor.challenge.feature.coindetails.domain.CoinDetails

class RefreshCoinDetailsUseCase(
    private val repository: Repository<String, CoinDetails?>
) {

    suspend operator fun invoke(id: String): Result<CoinDetails?> {
        return repository.fetchData(id, true)
    }
}