package com.example.stockmanager.data.repository

import com.example.stockmanager.data.model.StockResponse
import com.example.stockmanager.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class StockRepository  @Inject constructor(
    private val api: ApiService
) {
    suspend fun getStocks(): Response<StockResponse> {
        return api.getStocks()
    }
}