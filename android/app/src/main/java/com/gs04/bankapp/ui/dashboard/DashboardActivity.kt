package com.gs04.bankapp.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gs04.bankapp.databinding.ActivityDashboardBinding
import com.gs04.bankapp.ui.auth.LoginActivity // Importación correcta
import java.text.NumberFormat
import java.util.Locale

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recepción de datos básicos
        val userName = intent.getStringExtra("fullName") ?: "Usuario"
        val balance = intent.getDoubleExtra("balance", 0.0)

        // UI
        binding.tvWelcome.text = "¡Hola, $userName!"
        val formatoCOP = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
        binding.tvBalance.text = formatoCOP.format(balance)

        // Acción de cerrar sesión
        binding.btnLogout.setOnClickListener {
            // Usamos la clase importada arriba
            val intent = Intent(this, LoginActivity::class.java)
            // Esto es crucial para no tener errores de navegación
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }
}