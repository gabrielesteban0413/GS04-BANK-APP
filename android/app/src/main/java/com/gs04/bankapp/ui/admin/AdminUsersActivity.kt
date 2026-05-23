package com.gs04.bankapp.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.gs04.bankapp.R

class AdminUsersActivity : AppCompatActivity() {

    // 1. ESTO ES LO QUE TE FALTABA: Declarar las variables aquí arriba
    private lateinit var rvUsers: RecyclerView
    private lateinit var fabAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_users)

        // 2. Ahora sí, esto funcionará porque ya declaramos las variables arriba
        rvUsers = findViewById(R.id.rvUsers)
        fabAdd = findViewById(R.id.fabAdd)

        rvUsers.layoutManager = LinearLayoutManager(this)

        fabAdd.setOnClickListener {
            // Acción para el botón
        }
    }
}