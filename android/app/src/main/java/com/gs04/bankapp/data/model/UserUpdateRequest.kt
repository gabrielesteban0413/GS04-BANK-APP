package com.gs04.bankapp.data.model

data class UserUpdateRequest(
    val fullName: String? = null,
    val email: String? = null,
    val balance: Double? = null,
    val role: String? = null,
    val isActive: Boolean? = null
)
