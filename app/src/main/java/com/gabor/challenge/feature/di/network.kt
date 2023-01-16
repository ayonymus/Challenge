package com.gabor.challenge.feature.di

import com.gabor.challenge.BuildConfig
import com.gabor.challenge.feature.market.data.remote.CoingeckoMarketDataApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideLoggingInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideGsonConverterFactory() }
    factory { provideCoingeckoRetrofitBuilder(get(), get()) }
    factory { provideMarketApi(get()) }
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}

fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val builder = OkHttpClient().newBuilder()
    if (BuildConfig.DEBUG) {
        builder.addInterceptor(loggingInterceptor)
    }
    return builder.build()
}

fun provideGsonConverterFactory(): GsonConverterFactory {
    val gsonBuilder = GsonBuilder()
    return GsonConverterFactory.create(gsonBuilder.create())
}

fun provideCoingeckoRetrofitBuilder(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit = Retrofit.Builder()
    .baseUrl("https://api.coingecko.com/")
    .client(okHttpClient)
    .addConverterFactory(gsonConverterFactory)
    .build()

fun provideMarketApi(retrofit: Retrofit): CoingeckoMarketDataApi = retrofit.create(CoingeckoMarketDataApi::class.java)
