package com.example.stockmanager.data.remote

import com.example.stockmanager.data.model.LoginRequest
import com.example.stockmanager.data.model.LoginResponse
import com.example.stockmanager.data.model.SignUpRequest
import com.example.stockmanager.data.model.SignUpResponse
import com.example.stockmanager.data.model.StockRequest
import com.example.stockmanager.data.model.StockResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("v1/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("v1/sign_up")
    suspend fun signUp(@Body request: SignUpRequest): Response<SignUpResponse>

    @POST("v1/stocks")
    suspend fun createStock(@Body stock: StockRequest): Response<StockResponse>

    @GET("v1/stocks")
    suspend fun getStocks(): Response<StockResponse>

    @PUT("v1/stocks/{id}")
    suspend fun updateStock(
        @Path("id") id: String,
        @Body stock: StockRequest
    ): Response<StockRequest>

    @DELETE("v1/stocks/{id}")
    suspend fun deleteStock(@Path("id") id: String): Response<Unit>
}

