package com.gs04.bankapp.data.repository

import com.gs04.bankapp.data.model.LoginRequest
import com.gs04.bankapp.data.model.LoginResponse
import com.gs04.bankapp.data.network.RetrofitClient

class AuthRepository {
    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = RetrofitClient.apiService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Respuesta vacía"))
            } else {
                val error = response.errorBody()?.string() ?: "Error desconocido"
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
