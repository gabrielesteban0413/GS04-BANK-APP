package com.gs04.bankapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gs04.bankapp.databinding.ActivityLoginBinding
import com.gs04.bankapp.ui.dashboard.DashboardActivity // Importación asegurada
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        // 1. Botón de Login (el que usas para entrar)
        binding.btnRegister.setOnClickListener {
            val email = binding.etCedula.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.login(email, password)
            }
        }

        // 2. Link de Olvidé mi contraseña (INDEPENDIENTE)
        binding.tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        // 3. Link de Registro
        binding.tvRegisterLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupObservers() {
        viewModel.uiState.onEach { state ->
            when (state) {
                is LoginViewModel.LoginUiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnRegister.isEnabled = false // Ya está correcto
                }
                is LoginViewModel.LoginUiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnRegister.isEnabled = true

                    val data = state.data
                    val intent = Intent(this, DashboardActivity::class.java)
                    intent.putExtra("userId", data.userId)
                    intent.putExtra("fullName", data.fullName)
                    intent.putExtra("balance", data.balance)
                    intent.putExtra("role", data.role)

                    startActivity(intent)
                    finish()
                }
                is LoginViewModel.LoginUiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    // CORREGIDO: Cambiamos btnLogin por btnRegister
                    binding.btnRegister.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                    // CORREGIDO: Cambiamos btnLogin por btnRegister
                    binding.btnRegister.isEnabled = true
                }
            }
        }.launchIn(lifecycleScope)
    }
}