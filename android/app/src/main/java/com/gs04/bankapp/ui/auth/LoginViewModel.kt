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

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            val request = LoginRequest(email = email, password = password)
            val result = authRepository.login(request)

            // Manejo del resultado
            val data = result.getOrNull()

            if (data != null) {
                _uiState.value = LoginUiState.Success(data)
            } else {
                // El servidor envía un JSON complejo, pero solo queremos mostrar el error.
                // Si quieres algo amigable, podrías hacer algo así:
                _uiState.value = LoginUiState.Error("Correo o contraseña incorrectos")

                // O si prefieres mostrar lo que viene del servidor,
                // tendrías que usar una librería como Gson para "parsear" ese JSON
                // y extraer solo el campo "error".
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