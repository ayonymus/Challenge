package com.gabor.challenge.feature.market.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MarketDataEntity::class],
    version = 1,
    exportSchema = false)
abstract class MarketDataDatabase(): RoomDatabase() {

    abstract fun marketDataDao(): MarketDataDao

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
