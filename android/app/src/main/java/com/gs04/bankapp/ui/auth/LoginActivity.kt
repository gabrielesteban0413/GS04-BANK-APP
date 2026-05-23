package com.gs04.bankapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gs04.bankapp.databinding.ActivityLoginBinding
import com.gs04.bankapp.ui.dashboard.DashboardActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.login(email, password)
        }

        viewModel.uiState.onEach { state ->
            when (state) {
                is LoginViewModel.LoginUiState.Loading -> { // <--- Agrega el LoginViewModel. aquí
                    binding.progressBar.visibility = android.view.View.VISIBLE
                    binding.btnLogin.isEnabled = false
                }
                is LoginViewModel.LoginUiState.Success -> { // <--- Agrega el LoginViewModel. aquí
                    binding.progressBar.visibility = android.view.View.GONE
                    binding.btnLogin.isEnabled = true
                    // ... resto del código
                }
                is LoginViewModel.LoginUiState.Error -> {   // <--- Agrega el LoginViewModel. aquí
                    binding.progressBar.visibility = android.view.View.GONE
                    binding.btnLogin.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }.launchIn(lifecycleScope)
    }
}
