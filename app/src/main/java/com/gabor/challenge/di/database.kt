package com.gabor.challenge.di

import android.content.Context
import com.gabor.challenge.database.MarketDataDatabase
import org.koin.dsl.module

val marketDatabaseModule = module {
    single { createMarketDataDatabase(get()) }
    factory { createMarketDataDao(get()) }
    factory { createCoinDetailsDao(get()) }
}

fun createMarketDataDatabase(context: Context) = MarketDataDatabase.getDatabase(context)

fun createMarketDataDao(db: MarketDataDatabase) = db.marketDataDao()

fun createCoinDetailsDao(db: MarketDataDatabase) = db.coinDetailsDao()
