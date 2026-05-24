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

    private val _registerResult = MutableLiveData<Result<UserResponse?>>()
    val registerResult: LiveData<Result<UserResponse?>> = _registerResult

    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            // Como el repositorio ya devuelve un Result, solo asignamos el resultado
            val result = repository.register(request)
            _registerResult.value = result
        }
    }
}