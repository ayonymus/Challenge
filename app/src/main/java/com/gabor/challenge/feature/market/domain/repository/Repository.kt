package com.gabor.challenge.feature.market.domain.repository

interface Repository<Arg, Res> {

    suspend fun fetchData(currency: Arg, refresh: Boolean): Result<Res>

}
