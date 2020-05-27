package com.github.ratrofitwithmvc.model.api

import com.github.ratrofitwithmvc.model.data.DataResponse
import com.github.ratrofitwithmvc.model.data.UserData
import com.github.ratrofitwithmvc.model.data.UserRipoData
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiClient {

    companion object {
        operator fun invoke(): ApiClient {
            val client = OkHttpClient
                .Builder()
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.github.com/") // API Root path
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiClient::class.java)
        }
    }

    @Headers("content-type: application/json; charset=utf-8")
    @GET("search/users")
    fun getUserSearch(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Observable<DataResponse>

    @Headers("content-type: application/json; charset=utf-8")
    @GET("users/{user}")
    fun getUserInfo(@Path("user") user_name: String): Observable<UserData>

    @Headers("content-type: application/json; charset=utf-8")
    @GET("users/{user}/repos")
    fun getUserRipo(@Path("user") user_name: String): Observable<List<UserRipoData>>

}
