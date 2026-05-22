package com.gs04.bankapp.ui.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gs04.bankapp.data.model.TransferRequest
import com.gs04.bankapp.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransferViewModel : ViewModel() {
    private val _state = MutableStateFlow<TransferUiState>(TransferUiState.Idle)
    val state: StateFlow<TransferUiState> = _state

    fun transfer(userId: String, amount: Double, targetAccount: String, concept: String) {
        viewModelScope.launch {
            _state.value = TransferUiState.Loading
            val request = TransferRequest(amount, targetAccount, concept)
            val response = RetrofitClient.apiService.transfer(userId, request)
            if (response.isSuccessful) {
                _state.value = TransferUiState.Success("Transferencia exitosa")
            } else {
                val error = response.errorBody()?.string() ?: "Error desconocido"
                _state.value = TransferUiState.Error(error)
            }
        }
    }
}

sealed class TransferUiState {
    object Idle : TransferUiState()
    object Loading : TransferUiState()
    data class Success(val message: String) : TransferUiState()
    data class Error(val message: String) : TransferUiState()
}
