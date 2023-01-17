package com.gabor.challenge.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gabor.challenge.database.market.MarketDataDao
import com.gabor.challenge.database.market.MarketDataEntity
import com.gabor.challenge.database.coindetails.CoinDetailsEntity

@Database(
    entities = [MarketDataEntity::class, CoinDetailsEntity::class],
    version = 1,
    exportSchema = false)
abstract class MarketDataDatabase(): RoomDatabase() {

    abstract fun marketDataDao(): MarketDataDao

    abstract fun coinDetailsDao(): MarketDataDao

    companion object {

        @Volatile
        private var INSTANCE: MarketDataDatabase? = null

        fun getDatabase(context: Context): MarketDataDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarketDataDatabase::class.java,
                    "market_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
