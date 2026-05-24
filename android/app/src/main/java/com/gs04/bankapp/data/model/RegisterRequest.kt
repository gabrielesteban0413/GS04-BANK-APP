package com.gs04.bankapp.data.model

data class RegisterRequest(
    val cedula: String,
    val nombre: String,
    val email: String,
    val celular: String,
    val password: String,
    val apellido: String = "N/A" // Esto hace que sea opcional y no dé error
)