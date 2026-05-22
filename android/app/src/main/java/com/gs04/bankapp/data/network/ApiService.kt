package com.gs04.bankapp.data.network

import com.gs04.bankapp.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("api/auth/me")
    suspend fun getUserRole(@Header("X-User-Id") userId: String): Response<Map<String, String>>

    @POST("api/transactions/transfer")
    suspend fun transfer(
        @Header("X-User-Id") userId: String,
        @Body request: TransferRequest
    ): Response<Map<String, String>>

    @GET("api/transactions")
    suspend fun getUserTransactions(@Header("X-User-Id") userId: String): Response<List<Any>>

    @GET("api/admin/users")
    suspend fun getAllUsers(@Header("X-User-Id") adminUserId: String): Response<List<UserResponse>>

    @POST("api/admin/users")
    suspend fun createUser(
        @Header("X-User-Id") adminUserId: String,
        @Body request: UserCreateRequest
    ): Response<UserResponse>

    @PUT("api/admin/users/{userId}")
    suspend fun updateUser(
        @Header("X-User-Id") adminUserId: String,
        @Path("userId") targetUserId: String,
        @Body request: UserUpdateRequest
    ): Response<UserResponse>

    @DELETE("api/admin/users/{userId}")
    suspend fun deleteUser(
        @Header("X-User-Id") adminUserId: String,
        @Path("userId") targetUserId: String
    ): Response<Unit>
}
