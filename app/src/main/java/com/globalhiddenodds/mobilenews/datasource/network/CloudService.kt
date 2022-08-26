package com.globalhiddenodds.mobilenews.datasource.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val baseUrl = "https://hn.algolia.com/api/v1/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(baseUrl)
    .build()
interface INewsCloudService {
    @GET("search_by_date?query=android")
    suspend fun getNews(): Any
}
//Pattern Singleton
object NewsCloud {
    val retrofitService: INewsCloudService by lazy {
        retrofit.create(INewsCloudService::class.java)
    }
}