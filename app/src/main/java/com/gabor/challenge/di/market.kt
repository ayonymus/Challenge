package com.gabor.challenge.di

import android.content.Context
import com.gabor.challenge.feature.market.data.local.DatabaseLocalDataSource
import com.gabor.challenge.database.MarketDataDatabase
import com.gabor.challenge.feature.market.data.remote.RemoteMarketDataSource
import com.gabor.challenge.core.repository.CachingRepository
import com.gabor.challenge.core.repository.LocalDataSource
import com.gabor.challenge.core.repository.RemoteDataSource
import com.gabor.challenge.feature.market.domain.entites.FiatCurrency
import com.gabor.challenge.feature.market.domain.entites.MarketData
import com.gabor.challenge.core.repository.Repository
import com.gabor.challenge.feature.market.presentation.MarketViewModel
import com.gabor.challenge.feature.market.usecase.FetchMarketDataUseCase
import com.gabor.challenge.feature.market.usecase.RefreshMarketDataUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val marketDataModule = module {

    factory<RemoteDataSource<FiatCurrency, List<MarketData>>> { RemoteMarketDataSource(get()) }
    factory<LocalDataSource<FiatCurrency, List<MarketData>>> { DatabaseLocalDataSource(get()) }

    single { provideMarketDataRepository(get(), get()) }

}

fun provideMarketDataRepository(
    remoteMarketDataSource: RemoteDataSource<FiatCurrency, List<MarketData>>,
    localMarketDataSource: LocalDataSource<FiatCurrency, List<MarketData>>
): Repository<FiatCurrency, List<MarketData>> {
    return object : CachingRepository<FiatCurrency, List<MarketData>>(localMarketDataSource, remoteMarketDataSource) { }

}

val marketUiModule = module {
    factory { FetchMarketDataUseCase(get(), get()) }
    factory { RefreshMarketDataUseCase(get(), get()) }
    viewModel { MarketViewModel(get(), get()) }
}
