package com.gs04.bankapp.data.model

data class UserCreateRequest(
    val fullName: String,
    val email: String,
    val accountNumber: String,
    val password: String,
    val initialBalance: Double,
    val role: String
)
