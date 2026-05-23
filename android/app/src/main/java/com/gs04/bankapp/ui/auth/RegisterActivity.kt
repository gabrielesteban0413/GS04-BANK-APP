package com.gs04.bankapp.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.gs04.bankapp.databinding.ActivityRegisterBinding
import com.gs04.bankapp.data.model.RegisterRequest

class RegisterActivity : AppCompatActivity() {

    // 1. Inicializamos el binding y el viewModel
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2. Observar el resultado del registro
        viewModel.registerResult.observe(this) { result ->
            if (result.isSuccess) {
                Toast.makeText(this, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error: ${result.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
            }
        }

        // 3. Configurar el botón de registro
        binding.btnConfirmRegister.setOnClickListener {
            val cedula = binding.etCedula.text.toString().trim()
            val nombre = binding.etNombre.text.toString().trim()
            val apellido = binding.etApellido.text.toString().trim()
            val celular = binding.etCelular.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Validar campos
            if (cedula.isNotEmpty() && nombre.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val request = RegisterRequest(cedula, nombre, apellido, celular, email, password)
                viewModel.register(request)
            } else {
                Toast.makeText(this, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}