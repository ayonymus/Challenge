package com.gabor.challenge.feature.coindetails.domain

data class CoinDetails(
    val symbol: String,
    val name: String,
    val image: String?,
    val id: String,
    val homepage: String?,
    val description: String?,
    val genesisData: String?,
    val lastUpdated: String?
)