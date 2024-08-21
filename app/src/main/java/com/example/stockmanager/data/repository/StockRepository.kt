package com.example.stockmanager.data.repository

import com.example.stockmanager.data.model.StockListResponse
import com.example.stockmanager.data.model.StockRequest
import com.example.stockmanager.data.model.StockResponse
import com.example.stockmanager.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class StockRepository  @Inject constructor(
    private val api: ApiService
) {
    suspend fun getStocks(): Response<StockListResponse> {
        return api.getStocks()
    }

    suspend fun createStocks(request: StockRequest): Response<StockResponse> {
        return api.createStock(request)
    }

    suspend fun updateStocks(id: String, request: StockRequest): Response<StockResponse> {
        return api.updateStock(id, request)
    }
}