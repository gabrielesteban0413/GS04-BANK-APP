package com.gs04.bankapp.data.model

data class LoginResponse(
    val userId: String,
    val fullName: String,
    val accountNumber: String,
    val balance: Double,
    val role: String
)
