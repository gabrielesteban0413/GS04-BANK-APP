package com.gs04.bankapp.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gs04.bankapp.databinding.ActivityDashboardBinding
import com.gs04.bankapp.ui.admin.AdminUsersActivity
import com.gs04.bankapp.ui.transfer.TransferActivity

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private var userId: String = ""
    private var role: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getStringExtra("userId") ?: ""
        val userName = intent.getStringExtra("userName") ?: ""
        val balance = intent.getDoubleExtra("balance", 0.0)
        role = intent.getStringExtra("role") ?: "USER"

        binding.tvWelcome.text = "Bienvenido, $userName"
        binding.tvBalance.text = "Saldo: €${String.format("%.2f", balance)}"

        binding.btnTransfer.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        if (role == "ADMIN") {
            binding.btnAdmin.visibility = android.view.View.VISIBLE
            binding.btnAdmin.setOnClickListener {
                val intent = Intent(this, AdminUsersActivity::class.java)
                intent.putExtra("userId", userId)
                startActivity(intent)
            }
        }
    }
}
