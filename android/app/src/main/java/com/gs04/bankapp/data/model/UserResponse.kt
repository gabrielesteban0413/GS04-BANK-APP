package com.gs04.bankapp.data.model

data class UserResponse(
    val userId: String,
    val fullName: String,
    val email: String,
    val accountNumber: String,
    val balance: Double,
    val role: String,
    val isActive: Boolean
)
