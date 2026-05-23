package com.gs04.bankapp.ui.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gs04.bankapp.R
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

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                showError(getString(R.string.empty_fields_error))
                return@setOnClickListener
            }
            viewModel.login(email, password)
        }

        binding.tvRegisterLink.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun observeViewModel() {
        viewModel.uiState
            .onEach { state ->
                when (state) {
                    is LoginViewModel.LoginUiState.Loading -> showLoading(true)
                    is LoginViewModel.LoginUiState.Success -> handleSuccess()
                    is LoginViewModel.LoginUiState.Error -> showError(state.message)
                    else -> showLoading(false)
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
        binding.btnLogin.isEnabled = !isLoading
    }

    private fun showError(message: String) {
        showLoading(false)
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun handleSuccess() {
        showLoading(false)
        saveSession()
        navigateToDashboard()
        finish()
    }

    private fun saveSession() {
        getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .edit()
            .putString("auth_token", "logged_in")
            .apply()
    }

    private fun navigateToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
    }

    private fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}