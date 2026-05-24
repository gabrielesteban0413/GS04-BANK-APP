package com.gs04.bankapp.ui.cards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gs04.bankapp.R

class CardsAdapter(
    private val onDeleteClick: (Card) -> Unit
) : RecyclerView.Adapter<CardsAdapter.CardViewHolder>() {

    private var cards = emptyList<Card>()

    // NUEVO: siempre limita a 2 elementos
    fun submitList(list: List<Card>) {
        cards = list.take(2)  // <-- FUERZA SOLO DOS
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]
        holder.bind(card)
        holder.btnDelete.setOnClickListener { onDeleteClick(card) }
    }

    override fun getItemCount() = cards.size

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCardNumber: TextView = itemView.findViewById(R.id.tvCardNumber)
        private val tvCardHolder: TextView = itemView.findViewById(R.id.tvCardHolder)
        private val tvExpiry: TextView = itemView.findViewById(R.id.tvExpiry)
        private val tvCvv: TextView = itemView.findViewById(R.id.tvCvv)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDeleteCard)

        fun bind(card: Card) {
            tvCardNumber.text = card.cardNumber
            tvCardHolder.text = card.cardHolder
            tvExpiry.text = card.expiry
            tvCvv.text = card.cvv
        }
    }
}