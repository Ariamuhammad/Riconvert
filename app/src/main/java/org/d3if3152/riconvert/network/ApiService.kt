package org.d3if3152.riconvert.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if3152.riconvert.MataUang
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/Ariamuhammad/UangApi/main/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("uang-api.json")
    suspend fun getUang(): List<MataUang>
}

object MataUangApi {
    val service: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    fun getUangUrl(imageId: String): String {
        return "$BASE_URL$imageId.jpg"
    }

}

enum class ApiStatus { LOADING, SUCCES, FAILED}