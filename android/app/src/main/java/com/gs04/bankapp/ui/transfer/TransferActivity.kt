package com.gs04.bankapp.ui.transfer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gs04.bankapp.databinding.ActivityTransferBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TransferActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransferBinding
    private val viewModel: TransferViewModel by viewModels()
    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getStringExtra("userId") ?: ""

        binding.btnSubmitTransfer.setOnClickListener {
            val amount = binding.etAmount.text.toString().toDoubleOrNull()
            val targetAccount = binding.etTargetAccount.text.toString().trim()
            val concept = binding.etConcept.text.toString().trim()

            if (amount == null || amount <= 0) {
                Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (targetAccount.isEmpty()) {
                Toast.makeText(this, "Cuenta destino requerida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!targetAccount.matches(Regex("^ES[0-9]{2}$"))) {
                Toast.makeText(this, "Formato de cuenta: ES01, ES02, etc.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.transfer(userId, amount, targetAccount, if (concept.isEmpty()) "Sin concepto" else concept)
        }

        viewModel.state.onEach { state ->
            when (state) {
                is TransferUiState.Loading -> {
                    binding.progressBar.visibility = android.view.View.VISIBLE
                    binding.btnSubmitTransfer.isEnabled = false
                }
                is TransferUiState.Success -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    binding.btnSubmitTransfer.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                    finish()
                }
                is TransferUiState.Error -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    binding.btnSubmitTransfer.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }.launchIn(lifecycleScope)
    }
}
