package com.example.kakitanganapp

import androidx.recyclerview.widget.RecyclerView
import com.example.kakitanganapp.databinding.CardCellBinding

class CardViewHolder(private val cardCellBinding: CardCellBinding)
    : RecyclerView.ViewHolder(cardCellBinding.root) {
        fun bindMaid(maid: Maid){
            cardCellBinding.imageView1.setImageResource(maid.img)
            cardCellBinding.textView1.text = maid.name
            cardCellBinding.textView2.text = maid.age.toString()
            cardCellBinding.textView3.text = maid.description
        }
}