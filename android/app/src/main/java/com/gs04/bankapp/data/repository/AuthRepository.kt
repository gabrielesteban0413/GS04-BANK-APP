package com.gs04.bankapp.data.repository

import com.gs04.bankapp.data.model.LoginRequest
import com.gs04.bankapp.data.model.LoginResponse
import com.gs04.bankapp.data.network.RetrofitClient

class AuthRepository {

    suspend fun login(request: LoginRequest): Result<LoginResponse?> {
        return try {
            val response = RetrofitClient.apiService.login(request)
            if (response.isSuccessful) {
                // Aquí indicamos explícitamente que esperamos un LoginResponse
                Result.success(response.body())
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}