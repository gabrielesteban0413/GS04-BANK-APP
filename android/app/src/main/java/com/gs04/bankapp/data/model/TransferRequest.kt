package com.gs04.bankapp.data.model

data class TransferRequest(
    val amount: Double,
    val targetAccountNumber: String,
    val concept: String
)
