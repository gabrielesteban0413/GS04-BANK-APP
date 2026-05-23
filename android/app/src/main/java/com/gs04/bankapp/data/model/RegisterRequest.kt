package com.gs04.bankapp.data.model

data class RegisterRequest(
    val cedula: String,
    val nombre: String,
    val apellido: String,
    val celular: String,
    val email: String,
    val password: String,

)