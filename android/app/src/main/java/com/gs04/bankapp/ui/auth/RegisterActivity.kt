package com.gs04.bankapp.ui.auth

import android.os.Bundle
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

        // 1. Observar el resultado del registro desde el ViewModel
        viewModel.registerResult.observe(this) { result ->
            binding.progressBar.visibility = View.GONE
            binding.btnRegister.isEnabled = true

            if (result.isSuccess) {
                Toast.makeText(this, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error: ${result.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
            }
        }

        // 2. Configurar botón de registro
        binding.btnRegister.setOnClickListener {
            val cedula = binding.etCedula.text.toString().trim()
            val nombreCompleto = binding.etFullName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            // Validaciones básicas
            if (cedula.isEmpty() || nombreCompleto.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Preparar el envío (Ajusta los campos según lo que espera tu RegisterRequest)
            binding.progressBar.visibility = View.VISIBLE
            binding.btnRegister.isEnabled = false

            // Enviar al ViewModel (Asegúrate de pasar los valores correctos)
            val request = RegisterRequest(
                cedula = cedula,
                nombre = nombreCompleto,
                apellido = "N/A", // Si tu backend exige este campo, pon un valor por defecto o añádelo al XML
                celular = "0000000000",
                email = email,
                password = password
            )
            viewModel.register(request)
        }

        // 3. Link para volver al Login
        binding.tvLoginLink.setOnClickListener {
            finish()
        }
    }
}