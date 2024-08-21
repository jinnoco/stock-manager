package com.example.stockmanager.data.model

data class StockResponse(
    val statusCode: Int,
    val message: String,
    val result: StockResponseData
)

data class StockListResponse(
    val statusCode: Int,
    val message: String,
    val result: StockResponseResult
)

data class StockResponseResult(
    val data: List<StockResponseData>
)

data class StockResponseData(
    val id: String,
    val name: String,
    val image: String?,
    val purchaseDate: String,
)