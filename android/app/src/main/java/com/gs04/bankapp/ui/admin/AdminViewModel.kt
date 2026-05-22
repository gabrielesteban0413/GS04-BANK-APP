package com.gs04.bankapp.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gs04.bankapp.data.model.UserCreateRequest
import com.gs04.bankapp.data.model.UserResponse
import com.gs04.bankapp.data.model.UserUpdateRequest
import com.gs04.bankapp.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {
    private val _users = MutableStateFlow<List<UserResponse>>(emptyList())
    val users: StateFlow<List<UserResponse>> = _users

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    fun loadUsers(adminUserId: String) {
        viewModelScope.launch {
            _loading.value = true
            val response = RetrofitClient.apiService.getAllUsers(adminUserId)
            if (response.isSuccessful) {
                _users.value = response.body() ?: emptyList()
            } else {
                _message.value = response.errorBody()?.string() ?: "Error al cargar usuarios"
            }
            _loading.value = false
        }
    }

    fun createUser(adminUserId: String, request: UserCreateRequest) {
        viewModelScope.launch {
            _loading.value = true
            val response = RetrofitClient.apiService.createUser(adminUserId, request)
            if (response.isSuccessful) {
                loadUsers(adminUserId)
                _message.value = "Usuario creado"
            } else {
                _message.value = response.errorBody()?.string() ?: "Error al crear usuario"
            }
            _loading.value = false
        }
    }

    fun updateUser(adminUserId: String, targetUserId: String, request: UserUpdateRequest) {
        viewModelScope.launch {
            _loading.value = true
            val response = RetrofitClient.apiService.updateUser(adminUserId, targetUserId, request)
            if (response.isSuccessful) {
                loadUsers(adminUserId)
                _message.value = "Usuario actualizado"
            } else {
                _message.value = response.errorBody()?.string() ?: "Error al actualizar"
            }
            _loading.value = false
        }
    }

    fun deleteUser(adminUserId: String, targetUserId: String) {
        viewModelScope.launch {
            _loading.value = true
            val response = RetrofitClient.apiService.deleteUser(adminUserId, targetUserId)
            if (response.isSuccessful) {
                loadUsers(adminUserId)
                _message.value = "Usuario eliminado"
            } else {
                _message.value = response.errorBody()?.string() ?: "Error al eliminar"
            }
            _loading.value = false
        }
    }

    fun clearMessage() {
        _message.value = null
    }
}
