package com.gs04.bankapp.data.repository

import com.gs04.bankapp.data.model.*
import com.gs04.bankapp.data.network.RetrofitClient
import android.util.Log

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
                // Aquí recibimos el 201 Created. Si el cuerpo está vacío, response.body() será null,
                // lo cual es correcto si el tipo de retorno es UserResponse?
                Result.success(response.body())
            } else {
                val errorResponse = response.errorBody()?.string()
                Log.e("ERROR_DEBUG", "Cuerpo del error: $errorResponse")
                Log.e("ERROR_DEBUG", "Código de estado: ${response.code()}")
                Result.failure(Exception("Error ${response.code()}: $errorResponse"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}