package com.gs04.bankapp.data.network

import com.gs04.bankapp.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // --- MÓDULO DE AUTENTICACIÓN ---
    // El servidor espera /api/auth/login. Como la base ya tiene /api/, ponemos auth/login
    @POST("api/auth/login") // Ahora la suma será: BASE_URL + "api/auth/login"
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<UserResponse>

    @GET("auth/me")
    suspend fun getUserRole(@Header("X-User-Id") userId: String): Response<Map<String, String>>

    // --- MÓDULO ADMINISTRATIVO ---
    @POST("admin/users")
    suspend fun createUser(@Header("X-User-Id") adminUserId: String, @Body request: UserCreateRequest): Response<UserResponse>

    @GET("admin/users")
    suspend fun getAllUsers(@Header("X-User-Id") adminUserId: String): Response<List<UserResponse>>

    @PUT("admin/users/{userId}")
    suspend fun updateUser(
        @Header("X-User-Id") adminUserId: String,
        @Path("userId") targetUserId: String,
        @Body request: UserUpdateRequest
    ): Response<UserResponse>

    @DELETE("admin/users/{userId}")
    suspend fun deleteUser(
        @Header("X-User-Id") adminUserId: String,
        @Path("userId") targetUserId: String
    ): Response<Unit>

    // --- MÓDULO DE TRANSACCIONES ---
    @POST("transactions/transfer")
    suspend fun transfer(
        @Header("X-User-Id") userId: String,
        @Body request: TransferRequest
    ): Response<Map<String, String>>

    @GET("transactions")
    suspend fun getUserTransactions(@Header("X-User-Id") userId: String): Response<List<Any>>
}