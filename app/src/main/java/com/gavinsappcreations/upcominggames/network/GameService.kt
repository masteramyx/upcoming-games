package com.gavinsappcreations.upcominggames.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A retrofit service to fetch game release date.
 */
interface GameService {
    @GET("releases")
    suspend fun getGameData(
        @Query("api_key") apiKey: String,
        @Query("format") format: String,
        @Query("sort") sort: String,
        @Query("filter") filter: String,
        @Query("field_list") fieldList: String
    ): Response<NetworkReleasesContainer>
}

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Main entry point for network access. Call like `GameNetwork.gameData.getGameData()`
 */
object GameNetwork {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.giantbomb.com/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val gameData = retrofit.create(GameService::class.java)
}