#!/usr/bin/env kotlin
package com.gs04.bankapp.core.utils

import android.view.View
import android.widget.Toast
import android.content.Context

// Mostrar/ocultar vistas
fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

// Toast rápido
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

// Validación simple de string
fun String.isValidPin(): Boolean {
    return this.length == Constants.PIN_LENGTH && this.all { it.isDigit() }
}