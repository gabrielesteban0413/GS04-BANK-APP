package com.gs04.bankapp.ui.cards

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gs04.bankapp.databinding.ActivityMyCardsBinding
import kotlinx.coroutines.launch

class MyCardsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyCardsBinding
    private lateinit var adapter: CardsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadCards()

        binding.btnViewAllCards.setOnClickListener {
            startActivity(Intent(this, AllCardsActivity::class.java))
        }
        binding.btnEditCards.setOnClickListener {
            startActivity(Intent(this, AllCardsActivity::class.java))
        }
        binding.fabAddCard.setOnClickListener {
            startActivity(Intent(this, AllCardsActivity::class.java))
        }
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        adapter = CardsAdapter { card ->
            // No permitir eliminar desde aquí (solo desde AllCardsActivity)
        }
        binding.rvCards.layoutManager = LinearLayoutManager(this)
        binding.rvCards.adapter = adapter
    }

    private fun loadCards() {
        lifecycleScope.launch {
            // 🔁 REEMPLAZA ESTO con tu repositorio real
            val allCards = getSampleCards()  // ← aquí obtienes todas las tarjetas
            val firstTwo = allCards.take(2)  // ← solo las dos primeras
            adapter.submitList(firstTwo)

            if (firstTwo.isEmpty()) {
                binding.tvNoCards.visibility = View.VISIBLE
                binding.rvCards.visibility = View.GONE
            } else {
                binding.tvNoCards.visibility = View.GONE
                binding.rvCards.visibility = View.VISIBLE
            }
        }
    }

    // Ejemplo temporal - REEMPLAZA con tu fuente de datos real
    private fun getSampleCards(): List<Card> = listOf(
        Card("**** **** **** 1111", "Juan Pérez", "12/25", "123"),
        Card("**** **** **** 2222", "María López", "10/26", "456"),
        Card("**** **** **** 3333", "Carlos Gómez", "08/27", "789"),
        Card("**** **** **** 4444", "Ana Torres", "05/28", "012"),
        Card("**** **** **** 5555", "Luis Castro", "02/29", "345"),
        Card("**** **** **** 6666", "Elena Ruiz", "11/30", "678"),
        Card("**** **** **** 7777", "Pedro Díaz", "09/31", "901")
    )
}