package com.gs04.bankapp.ui.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gs04.bankapp.databinding.ItemUserBinding
import com.gs04.bankapp.data.model.UserResponse

class UserAdapter(
    private val onEdit: (UserResponse) -> Unit,
    private val onDelete: (UserResponse) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var users = listOf<UserResponse>()

    fun submitList(list: List<UserResponse>) {
        users = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position], onEdit, onDelete)
    }

    override fun getItemCount() = users.size

    class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserResponse, onEdit: (UserResponse) -> Unit, onDelete: (UserResponse) -> Unit) {
            binding.tvName.text = user.fullName
            binding.tvEmail.text = user.email
            binding.tvAccount.text = "Cuenta: ${user.accountNumber}"
            binding.tvBalance.text = "Saldo: €${String.format("%.2f", user.balance)}"
            binding.tvRole.text = "Rol: ${user.role}"
            binding.btnEdit.setOnClickListener { onEdit(user) }
            binding.btnDelete.setOnClickListener { onDelete(user) }
        }
    }
}
