package com.gabor.challenge.di

import com.gabor.challenge.core.repository.CachingRepository
import com.gabor.challenge.core.repository.LocalDataSource
import com.gabor.challenge.core.repository.RemoteDataSource
import com.gabor.challenge.core.repository.Repository
import com.gabor.challenge.feature.market.data.local.DatabaseLocalDataSource
import com.gabor.challenge.feature.market.data.remote.RemoteMarketDataSource
import com.gabor.challenge.feature.market.domain.FiatCurrency
import com.gabor.challenge.feature.market.domain.MarketData
import com.gabor.challenge.feature.market.presentation.MarketViewModel
import com.gabor.challenge.feature.market.usecase.FetchMarketDataUseCase
import com.gabor.challenge.feature.market.usecase.RefreshMarketDataUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val marketDataModule = module {
    factory<RemoteDataSource<FiatCurrency, List<MarketData>>>(named("remoteMarketSource")) { RemoteMarketDataSource(get()) }
    factory<LocalDataSource<FiatCurrency, List<MarketData>>>(named("localMarketSource")) { DatabaseLocalDataSource(get()) }
    single (named("marketRepo")) { provideMarketDataRepository(get(named("remoteMarketSource")), get(named("localMarketSource"))) }
}

fun provideMarketDataRepository(
    remoteMarketDataSource: RemoteDataSource<FiatCurrency, List<MarketData>>,
    localMarketDataSource: LocalDataSource<FiatCurrency, List<MarketData>>
): Repository<FiatCurrency, List<MarketData>> {
    return object : CachingRepository<FiatCurrency, List<MarketData>>(localMarketDataSource, remoteMarketDataSource) { }
}

val marketUseCaseModule = module {
    factory { FetchMarketDataUseCase(get(named("marketRepo")), get()) }
    factory { RefreshMarketDataUseCase(get(named("marketRepo")), get()) }
}

val marketUiModule = module {
    viewModel { MarketViewModel(get(), get()) }
}
