package com.gs04.bankapp.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.gs04.bankapp.databinding.ActivityRegisterBinding
import com.gs04.bankapp.data.model.RegisterRequest

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRealTimeValidation()
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.registerResult.observe(this) { result ->
            binding.progressBar.visibility = View.GONE
            binding.btnRegister.isEnabled = true

            if (result.isSuccess) {
                Toast.makeText(this, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                // Aquí verás el error real que viene del servidor
                Toast.makeText(this, "Error: ${result.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            val cedula = binding.etCedula.text.toString().trim()
            val nombre = binding.etFullName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val celular = binding.etCelular.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            // Resetear errores
            binding.tilPassword.error = null

            // Validaciones básicas
            if (cedula.isEmpty() || nombre.isEmpty() || email.isEmpty() || celular.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6 || password.length > 12) {
                binding.tilPassword.error = "Debe tener entre 6 y 12 caracteres"
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Iniciar registro
            binding.progressBar.visibility = View.VISIBLE
            binding.btnRegister.isEnabled = false

            viewModel.register(RegisterRequest(cedula, nombre, email, celular, password))
        }
    }

    private fun setupRealTimeValidation() {
        // Validación de celular en tiempo real
        binding.etCelular.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.isNotEmpty() && s.length != 10) {
                    binding.etCelular.error = "Debe tener 10 dígitos"
                } else {
                    binding.etCelular.error = null
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}