package com.gs04.bankapp.ui.cards

data class Card(
    val cardNumber: String,
    val cardHolder: String,
    val expiry: String,
    val cvv: String
)