package com.example.stockmanager.data.repository

import com.example.stockmanager.data.model.LoginRequest
import com.example.stockmanager.data.model.LoginResponse
import com.example.stockmanager.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: ApiService,
) {

    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return api.login(LoginRequest(request.email, request.password))
    }

}