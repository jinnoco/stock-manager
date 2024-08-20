package com.example.stockmanager.data.model

data class SignUpResponse(
    val statusCode: Int,
    val message: String,
    val result: SignupResponseResult
)

data class SignupResponseResult(
    val token: String?
)
