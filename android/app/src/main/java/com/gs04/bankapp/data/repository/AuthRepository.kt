package com.gs04.bankapp.data.repository

import com.gs04.bankapp.data.model.*
import com.gs04.bankapp.data.network.RetrofitClient
import retrofit2.Response

class AuthRepository {

    // --- FUNCIÓN DE LOGIN ---
    suspend fun login(request: LoginRequest): Result<LoginResponse?> {
        return try {
            val response = RetrofitClient.apiService.login(request)
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                val errorMsg = response.errorBody()?.string()?.replace("\"", "") ?: "Error de autenticación"
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- FUNCIÓN DE REGISTRO ---
    suspend fun register(request: RegisterRequest): Result<UserResponse?> {
        return try {
            val response = RetrofitClient.apiService.register(request)
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                val errorMsg = response.errorBody()?.string()?.replace("\"", "") ?: "Error al registrar"
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}