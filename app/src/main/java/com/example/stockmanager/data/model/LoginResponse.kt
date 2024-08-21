package com.example.stockmanager.data.model

data class LoginResponse(
    val statusCode: Int,
    val message: String,
    val result: LoginResponseResult
)

data class LoginResponseResult(
    val token: String?
)