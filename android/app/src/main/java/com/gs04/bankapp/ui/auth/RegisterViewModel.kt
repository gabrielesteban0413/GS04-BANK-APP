package com.gs04.bankapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gs04.bankapp.data.model.RegisterRequest
import com.gs04.bankapp.data.model.UserResponse
import com.gs04.bankapp.data.repository.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val repository = AuthRepository()

    // Este es el estado que la Activity observará
    private val _registerResult = MutableLiveData<Result<UserResponse?>>()
    val registerResult: LiveData<Result<UserResponse?>> = _registerResult

    // Solo una función de registro
    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            val result = repository.register(request)
            _registerResult.value = result
        }
    }
}