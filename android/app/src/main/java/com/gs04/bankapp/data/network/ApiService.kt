package com.gs04.bankapp.data.network

import com.gs04.bankapp.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // --- MÓDULO DE AUTENTICACIÓN Y REGISTRO ---
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    @POST("auth/register")
    suspend fun createUser(
        @Header("X-User-Id") adminUserId: String,
        @Body request: UserCreateRequest
    ): Response<UserResponse>

    // Este es el endpoint legítimo que probamos en Postman para registrar clientes
    @POST("auth/register")
    suspend fun createUser(@Body request: UserCreateRequest): Response<UserResponse>

    @GET("auth/me")
    suspend fun getUserRole(@Header("X-User-Id") userId: String): Response<Map<String, String>>


    // --- MÓDULO DE TRANSACCIONES ---

    @POST("transactions/transfer")
    suspend fun transfer(
        @Header("X-User-Id") userId: String,
        @Body request: TransferRequest
    ): Response<Map<String, String>>

    @GET("transactions")
    suspend fun getUserTransactions(@Header("X-User-Id") userId: String): Response<List<Any>>


    // --- MÓDULO ADMINISTRATIVO / CRUD DE USUARIOS ---

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
}