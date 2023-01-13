package com.gabor.challenge.feature.market.domain.repository

interface Repository<Arg, Res> {

    suspend fun fetchData(arg: Arg, refresh: Boolean): Result<Res>

}
