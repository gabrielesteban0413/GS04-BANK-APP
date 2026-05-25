package com.gs04.bankapp.ui.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gs04.bankapp.R

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Referencias a las vistas del XML
        val btnSend = findViewById<Button>(R.id.btnSendInstructions)
        val etIdentifier = findViewById<EditText>(R.id.etIdentifier)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tvBackToLogin = findViewById<TextView>(R.id.tvBackToLogin)

        // Acción del botón enviar
        btnSend.setOnClickListener {
            val input = etIdentifier.text.toString()
            if (input.isNotEmpty()) {
                // Simulamos carga
                btnSend.isEnabled = false
                progressBar.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    Toast.makeText(this, "Instrucciones enviadas a $input", Toast.LENGTH_LONG).show()
                    finish() // Regresa al Login
                }, 2000)
            } else {
                etIdentifier.error = "Campo requerido"
            }
        }

        // Acción del enlace para volver
        tvBackToLogin.setOnClickListener {
            finish()
        }
    }
}