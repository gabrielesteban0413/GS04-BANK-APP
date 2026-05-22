package com.gs04.bankapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gs04.bankapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            val result = authRepository.login(email, password)
            _uiState.value = when {
                result.isSuccess -> LoginUiState.Success(result.getOrNull()!!)
                else -> LoginUiState.Error(result.exceptionOrNull()?.message ?: "Error de red")
            }
        }
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val data: LoginResponse) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
