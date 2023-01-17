package com.gabor.challenge.database.coindetails

import androidx.room.*


@Dao
interface CoinDetailsDao {

    @Query("SELECT * FROM $COIN_DETAILS_TABLE WHERE id=:id" )
    suspend fun getCoinDetails(id: String): CoinDetailsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinDetails(data: CoinDetailsEntity)

    @Query("DELETE FROM $COIN_DETAILS_TABLE")
    suspend fun clear()

    @Query("DELETE FROM $COIN_DETAILS_TABLE WHERE id=:id")
    suspend fun deleteById(id: String)

}

const val COIN_DETAILS_TABLE = "COIN_DETAILS_TABLE"

@Entity(tableName = COIN_DETAILS_TABLE)
data class CoinDetailsEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val symbol: String,
    val name: String,
    val image: String?,
    val homepage: String?,
    val description: String?,
    val lastUpdated: String?
)
