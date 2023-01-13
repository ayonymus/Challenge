package com.gabor.challenge.feature.market.data.repository

import com.gabor.challenge.feature.market.domain.repository.Repository

/**
 * A repository that backs up data fetched from remote into a local cache.
 */
abstract class CachingRepository<Arg, Data>(
    private val localDataSource: LocalDataSource<Arg, Data>,
    private val remoteDataSource: RemoteDataSource<Arg, Data>
): Repository<Arg, Data> {

    /**
     * First fetch from local data source.
     * If not available or refresh is `true`, try fetching from remote.
     * Once new data is available, write to database.
     */
    override suspend fun fetchData(arg: Arg, refresh: Boolean): Result<Data> {
        if (refresh) {
            val result = remoteDataSource.fetch(arg)
            result.fold(
                onSuccess = {
                    localDataSource.update(arg, it)
                    return result
                },
                onFailure = {
                    return Result.failure(it)
                }
            )
        }
        return localDataSource.fetch(arg)
    }
}

interface LocalDataSource<Arg, Data> {

    suspend fun fetch(arg: Arg): Result<Data>

    suspend fun update(arg: Arg, data: Data)

}

interface RemoteDataSource<Arg, Data> {

    suspend fun fetch(arg: Arg): Result<Data>

}
