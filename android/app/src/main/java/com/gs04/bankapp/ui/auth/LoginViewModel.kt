package com.gs04.bankapp.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gs04.bankapp.data.model.LoginRequest
import com.gs04.bankapp.data.model.LoginResponse
import com.gs04.bankapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(cedula: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            // Enviamos la cédula como "email" porque tu DTO (LoginRequest)
            // probablemente espera el nombre de campo "email"
            val request = LoginRequest(email = cedula, password = password)
            val result = authRepository.login(request)
            val data = result.getOrNull()

            if (data != null) {
                _uiState.value = LoginUiState.Success(data)
            } else {
                // Mensaje coherente con la cédula
                Log.e("LOGIN_DEBUG", "Error en login: ${result.exceptionOrNull()?.message}")
                _uiState.value = LoginUiState.Error("Error: ${result.exceptionOrNull()?.message ?: "Cédula o contraseña incorrectos"}")
            }
        }
    }

    sealed class LoginUiState {
        object Idle : LoginUiState()
        object Loading : LoginUiState()
        data class Success(val data: LoginResponse) : LoginUiState()
        data class Error(val message: String) : LoginUiState()
    }
}