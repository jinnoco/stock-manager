package com.example.stockmanager.data.repository

import com.example.stockmanager.data.model.SignUpRequest
import com.example.stockmanager.data.model.SignUpResponse
import com.example.stockmanager.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class SignupRepository @Inject constructor(
    private val api: ApiService,
) {

    suspend fun signup(request: SignUpRequest): Response<SignUpResponse> {
        return api.signUp(SignUpRequest(request.email, request.password))
    }

}