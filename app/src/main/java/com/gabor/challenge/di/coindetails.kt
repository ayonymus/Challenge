package com.gabor.challenge.di

import com.gabor.challenge.core.repository.CachingRepository
import com.gabor.challenge.core.repository.LocalDataSource
import com.gabor.challenge.core.repository.RemoteDataSource
import com.gabor.challenge.core.repository.Repository
import com.gabor.challenge.feature.coindetails.data.local.LocalCoinDetailsDataSource
import com.gabor.challenge.feature.coindetails.data.remote.RemoteCoinDetailsSource
import com.gabor.challenge.feature.coindetails.domain.CoinDetails
import com.gabor.challenge.feature.coindetails.presentation.CoinDetailsViewModel
import com.gabor.challenge.feature.coindetails.usecase.FetchCoinDetailsUseCase
import com.gabor.challenge.feature.coindetails.usecase.RefreshCoinDetailsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coinDetailsModule = module {
    factory<RemoteDataSource<String, CoinDetails?>>(named("remoteCoinDetails")) { RemoteCoinDetailsSource(get()) }
    factory<LocalDataSource<String, CoinDetails?>>(named("localCoinDetails")) { LocalCoinDetailsDataSource(get()) }
    single (named("detailsRepo")) { provideCoinDetailsRepository(get(named("remoteCoinDetails")), get(named("localCoinDetails"))) }
}

fun provideCoinDetailsRepository(
    remoteCoinDetailsDataSource: RemoteDataSource<String, CoinDetails?>,
    localCoinDetailsDataSource: LocalDataSource<String, CoinDetails?>
): Repository<String, CoinDetails?> {
    return object : CachingRepository<String, CoinDetails?>(localCoinDetailsDataSource, remoteCoinDetailsDataSource) { }
}

val coinDetailsUseCaseModule = module {
    factory { FetchCoinDetailsUseCase(get(named("detailsRepo"))) }
    factory { RefreshCoinDetailsUseCase(get(named("detailsRepo"))) }
}

val coinDetailsUiModule = module {
    viewModel { CoinDetailsViewModel(get(), get())}
}
