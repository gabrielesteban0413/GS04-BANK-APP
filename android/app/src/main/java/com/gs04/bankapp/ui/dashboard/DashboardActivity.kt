package com.gs04.bankapp.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gs04.bankapp.databinding.ActivityDashboardBinding
import com.gs04.bankapp.ui.admin.AdminUsersActivity
import com.gs04.bankapp.ui.transfer.TransferActivity
import java.text.NumberFormat
import java.util.Locale

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private var userId: String = ""
    private var role: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Recepción de datos
        userId = intent.getStringExtra("userId") ?: ""
        val userName = intent.getStringExtra("fullName") ?: "Usuario"
        val balance = intent.getDoubleExtra("balance", 0.0)
        role = intent.getStringExtra("role") ?: "USER"

        // 2. Actualización de textos con diseño profesional
        binding.tvWelcome.text = "¡Hola, $userName!"

        // Formato de moneda para Colombia (COP)
        val formatoCOP = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
        binding.tvBalance.text = formatoCOP.format(balance)

        // 3. Acción del botón Transferir
        binding.btnTransfer.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        // 4. Lógica de Administrador
        if (role == "ADMIN") {
            binding.btnAdmin.visibility = View.VISIBLE
            binding.btnAdmin.setOnClickListener {
                val intent = Intent(this, AdminUsersActivity::class.java)
                intent.putExtra("userId", userId)
                startActivity(intent)
            }
        } else {
            binding.btnAdmin.visibility = View.GONE
        }
    }
}