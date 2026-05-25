package com.gs04.bankapp.ui.cards

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gs04.bankapp.databinding.ActivityAllCardsBinding
import kotlinx.coroutines.launch

class AllCardsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllCardsBinding
    private lateinit var adapter: CardsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllCardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadAllCards()

        binding.fabAddCard.setOnClickListener {
            showAddCardDialog()
        }
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        adapter = CardsAdapter { card ->
            showDeleteConfirmation(card)
        }
        binding.rvAllCards.layoutManager = LinearLayoutManager(this)
        binding.rvAllCards.adapter = adapter
    }

    private fun loadAllCards() {
        lifecycleScope.launch {
            // 🔁 REEMPLAZA esto con tu repositorio real
            val allCards = getSampleCards()   // ← lista completa
            adapter.submitList(allCards)
            if (allCards.isEmpty()) {
                binding.tvNoCards.visibility = View.VISIBLE
                binding.rvAllCards.visibility = View.GONE
            } else {
                binding.tvNoCards.visibility = View.GONE
                binding.rvAllCards.visibility = View.VISIBLE
            }
        }
    }

    private fun showAddCardDialog() {
        // TODO: Implementa un diálogo con campos para nueva tarjeta
        // Al guardar, agrega a la lista y refresca el adaptador
    }

    private fun showDeleteConfirmation(card: Card) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar tarjeta")
            .setMessage("¿Eliminar la tarjeta ${card.cardNumber}?")
            .setPositiveButton("Eliminar") { _, _ ->
                // TODO: Eliminar de la base de datos
                // Luego recargar lista:
                loadAllCards()
            }
            .setNegativeButton("Cancelar", null)
            .show()
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