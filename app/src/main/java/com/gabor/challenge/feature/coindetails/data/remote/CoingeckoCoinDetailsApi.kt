package com.gabor.challenge.feature.coindetails.data.remote

import com.gabor.challenge.feature.coindetails.domain.CoinDetails
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoingeckoCoinDetailsApi {

    @GET("api/v3/coins/{id}")
    suspend fun fetchMarketData(@Path("id") id: String): Response<CoingeckoCoinDetails>

}

data class CoingeckoCoinDetails(
    @SerializedName("id") val id: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("name") val name: String,
    @SerializedName("categories") val categories: ArrayList<String> = arrayListOf(),
    @SerializedName("public_notice") val publicNotice: String? = null,
    @SerializedName("additional_notices") val additionalNotices: ArrayList<String> = arrayListOf(),
    @SerializedName("description") val description: Description? = Description(),
    @SerializedName("links") val links: Links? = Links(),
    @SerializedName("image") val image: Image? = Image(),
    @SerializedName("country_origin") val countryOrigin: String? = null,
    @SerializedName("genesis_date") val genesisDate: String? = null,
    @SerializedName("sentiment_votes_up_percentage") val sentimentVotesUpPercentage: Int? = null,
    @SerializedName("sentiment_votes_down_percentage") val sentimentVotesDownPercentage: Int? = null,
    @SerializedName("market_cap_rank") val marketCapRank: Int? = null,
    @SerializedName("coingecko_rank") val coingeckoRank: Int? = null,
    @SerializedName("coingecko_score") val coingeckoScore: Double? = null,
    @SerializedName("developer_score") val developerScore: Double? = null,
    @SerializedName("community_score") val communityScore: Double? = null,
    @SerializedName("liquidity_score") val liquidityScore: Double? = null,
    @SerializedName("public_interest_score") val publicInterestScore: Double? = null,
    @SerializedName("status_updates") val statusUpdates: ArrayList<String> = arrayListOf(),
    @SerializedName("last_updated") val lastUpdated: String? = null
)

data class Description (var en : String? = null)

data class Links (val homepage: ArrayList<String> = arrayListOf())

data class Image (val large: String? = null)


fun CoingeckoCoinDetails.toCoinDetails() = CoinDetails(
    symbol = this.symbol,
    name = this.name,
    image = this.image?.large,
    id = this.id,
    homepage = this.links?.homepage?.get(0),
    description = this.description?.en,
    genesisData = this.genesisDate,
    lastUpdated = this.lastUpdated
)
