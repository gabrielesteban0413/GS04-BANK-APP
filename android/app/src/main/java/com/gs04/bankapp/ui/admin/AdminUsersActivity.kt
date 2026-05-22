package com.gs04.bankapp.ui.admin

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gs04.bankapp.databinding.ActivityAdminUsersBinding
import com.gs04.bankapp.data.model.UserCreateRequest
import com.gs04.bankapp.data.model.UserUpdateRequest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AdminUsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminUsersBinding
    private val viewModel: AdminViewModel by viewModels()
    private lateinit var adapter: UserAdapter
    private var adminUserId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adminUserId = intent.getStringExtra("userId") ?: run {
            finish()
            return
        }

        adapter = UserAdapter(
            onEdit = { user -> showEditDialog(user) },
            onDelete = { user -> showDeleteConfirm(user) }
        )
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter

        binding.fabAdd.setOnClickListener { showCreateDialog() }

        viewModel.loadUsers(adminUserId)

        viewModel.users.onEach { users ->
            adapter.submitList(users)
        }.launchIn(lifecycleScope)

        viewModel.message.onEach { msg ->
            msg?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.clearMessage()
            }
        }.launchIn(lifecycleScope)
    }

    private fun showCreateDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_user_form, null)
        val etFullName = dialogView.findViewById<EditText>(R.id.etFullName)
        val etEmail = dialogView.findViewById<EditText>(R.id.etEmail)
        val etAccount = dialogView.findViewById<EditText>(R.id.etAccount)
        val etPassword = dialogView.findViewById<EditText>(R.id.etPassword)
        val etBalance = dialogView.findViewById<EditText>(R.id.etBalance)
        val spinnerRole = dialogView.findViewById<Spinner>(R.id.spinnerRole)
        ArrayAdapter.createFromResource(this, R.array.roles_array, android.R.layout.simple_spinner_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerRole.adapter = it
        }

        AlertDialog.Builder(this)
            .setTitle("Crear usuario")
            .setView(dialogView)
            .setPositiveButton("Crear") { _, _ ->
                val fullName = etFullName.text.toString()
                val email = etEmail.text.toString()
                val account = etAccount.text.toString()
                val password = etPassword.text.toString()
                val balance = etBalance.text.toString().toDoubleOrNull() ?: 0.0
                val role = spinnerRole.selectedItem.toString()
                if (fullName.isNotBlank() && email.isNotBlank() && account.isNotBlank() && password.isNotBlank()) {
                    val request = UserCreateRequest(fullName, email, account, password, balance, role)
                    viewModel.createUser(adminUserId, request)
                } else {
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun showEditDialog(user: UserResponse) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_user_edit, null)
        val etFullName = dialogView.findViewById<EditText>(R.id.etFullName)
        val etEmail = dialogView.findViewById<EditText>(R.id.etEmail)
        val etBalance = dialogView.findViewById<EditText>(R.id.etBalance)
        val spinnerRole = dialogView.findViewById<Spinner>(R.id.spinnerRole)
        val cbActive = dialogView.findViewById<CheckBox>(R.id.cbActive)

        etFullName.setText(user.fullName)
        etEmail.setText(user.email)
        etBalance.setText(user.balance.toString())
        cbActive.isChecked = user.isActive

        ArrayAdapter.createFromResource(this, R.array.roles_array, android.R.layout.simple_spinner_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerRole.adapter = it
            val pos = if (user.role == "ADMIN") 0 else 1
            spinnerRole.setSelection(pos)
        }

        AlertDialog.Builder(this)
            .setTitle("Editar usuario")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val request = UserUpdateRequest(
                    fullName = etFullName.text.toString().takeIf { it != user.fullName },
                    email = etEmail.text.toString().takeIf { it != user.email },
                    balance = etBalance.text.toString().toDoubleOrNull()?.takeIf { it != user.balance },
                    role = spinnerRole.selectedItem.toString().takeIf { it != user.role },
                    isActive = cbActive.isChecked.takeIf { it != user.isActive }
                )
                viewModel.updateUser(adminUserId, user.userId, request)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun showDeleteConfirm(user: UserResponse) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar usuario")
            .setMessage("¿Eliminar a ${user.fullName}?")
            .setPositiveButton("Eliminar") { _, _ ->
                viewModel.deleteUser(adminUserId, user.userId)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
