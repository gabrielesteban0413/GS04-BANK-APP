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

    // Datos de ejemplo para el selector de receptor (mock)
    private val mockReceivers = listOf(
        Receiver("María González", "987654321", "ES01"),
        Receiver("Carlos López", "555123456", "ES02"),
        Receiver("Ana Martínez", "444987654", "ES03")
    )
    private var currentReceiverIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getStringExtra("userId") ?: ""

        setupMockData()
        setupListeners()
        observeViewModel()
    }

    private fun setupMockData() {
        // Cargar el primer receptor por defecto
        updateReceiverUI(mockReceivers[currentReceiverIndex])

        // Datos mock de cuenta origen
        binding.tvAccountName.text = "Cuenta Principal"
        binding.tvAccountBalance.text = "Saldo disponible: $5,250.00"
        // El logo ya se muestra gracias al layout (ivLogo)
    }

    private fun updateReceiverUI(receiver: Receiver) {
        binding.tvReceptorName.text = receiver.name
        binding.tvReceptorId.text = "Cédula: ${receiver.id}"
        // Opcional: cambiar avatar según receptor (aquí podrías cargar una imagen diferente)
        // binding.ivAvatar.setImageResource(receiver.avatarRes)
    }

    private fun setupListeners() {
        // Botón de retroceso
        binding.btnBack.setOnClickListener { finish() }

        // Botón para cambiar receptor (mock)
        binding.btnChangeReceiver.setOnClickListener {
            currentReceiverIndex = (currentReceiverIndex + 1) % mockReceivers.size
            val newReceiver = mockReceivers[currentReceiverIndex]
            updateReceiverUI(newReceiver)
            // Auto-completar la cuenta destino con la cuenta sugerida del receptor (mock)
            binding.etTargetAccount.setText(newReceiver.defaultAccount)
            Toast.makeText(this, "Receptor cambiado a ${newReceiver.name}", Toast.LENGTH_SHORT).show()
        }

        // Botón para cambiar cuenta origen (mock: alterna entre dos cuentas)
        binding.btnChangeAccount.setOnClickListener {
            val currentName = binding.tvAccountName.text.toString()
            if (currentName == "Cuenta Principal") {
                binding.tvAccountName.text = "Cuenta de Ahorros"
                binding.tvAccountBalance.text = "Saldo disponible: $15,000.00"
            } else {
                binding.tvAccountName.text = "Cuenta Principal"
                binding.tvAccountBalance.text = "Saldo disponible: $5,250.00"
            }
            Toast.makeText(this, "Cuenta cambiada", Toast.LENGTH_SHORT).show()
        }

        // Botones de montos rápidos
        binding.btnAmount10.setOnClickListener { binding.etAmount.setText("10") }
        binding.btnAmount20.setOnClickListener { binding.etAmount.setText("20") }
        binding.btnAmount50.setOnClickListener { binding.etAmount.setText("50") }
        binding.btnAmount100.setOnClickListener { binding.etAmount.setText("100") }

        // Botón principal de transferencia (Continuar)
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
            viewModel.transfer(
                userId = userId,
                amount = amount,
                targetAccount = targetAccount,
                concept = if (concept.isEmpty()) "Sin concepto" else concept
            )
        }
    }

    private fun observeViewModel() {
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
                else -> { }
            }
        }.launchIn(lifecycleScope)
    }

    // Clase de datos auxiliar para los receptores de prueba
    data class Receiver(val name: String, val id: String, val defaultAccount: String)
}