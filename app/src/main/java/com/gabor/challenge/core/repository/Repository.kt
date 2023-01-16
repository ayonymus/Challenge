package com.gabor.challenge.core.repository

interface Repository<Arg, Res> {

    suspend fun fetchData(arg: Arg, refresh: Boolean): Result<Res>

}
