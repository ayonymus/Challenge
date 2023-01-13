package com.gabor.challenge.feature.market.data.repository

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

private const val ARG = "argument"
private const val LOCAL_DATA = "local data"
private const val REMOTE_DATA = "remote data"


class CachingRepositoryTest {

    private val localDataSource: LocalDataSource<String, String> = mock()
    private val remoteDataSource: RemoteDataSource<String, String> = mock()

    private lateinit var repository: CachingRepository<String, String>

    @Before
    fun setUp() = runBlocking {
        whenever(localDataSource.fetch(ARG)).thenReturn(Result.success(LOCAL_DATA))
        whenever(remoteDataSource.fetch(ARG)).thenReturn(Result.success(REMOTE_DATA))
        repository = object : CachingRepository<String, String>(localDataSource, remoteDataSource) {}
    }

    @Test
    fun `GIVEN data present in local data source WHEN fetch THEN return local data`() = runTest {
        val result = repository.fetchData(ARG, false)

        assertEquals(Result.success(LOCAL_DATA), result)

        verifyNoMoreInteractions(remoteDataSource)
    }

    @Test
    fun `GIVEN data present in local data source WHEN fetch with refresh THEN return remote data`() = runTest {
        val result = repository.fetchData(ARG, true)

        assertEquals(Result.success(REMOTE_DATA), result)

        verify(localDataSource).update(ARG, REMOTE_DATA)
    }

    @Test
    fun `GIVEN data present in local data source WHEN remote errors THEN return error`() = runTest {
        val throwable = IOException()
        whenever(remoteDataSource.fetch(ARG)).thenReturn(Result.failure(throwable))
        val result = repository.fetchData(ARG, true)
        assertEquals(Result.failure<String>(throwable), result)

        verifyNoMoreInteractions(localDataSource)
    }

}