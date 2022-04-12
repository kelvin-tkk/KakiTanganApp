package com.example.kakitanganapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapterMaid(val context: Context): RecyclerView.Adapter<RecyclerAdapterMaid.ViewHolder>() {

    private var maids = arrayOf("Siti","Habibi","Lili", "Shankaria")
    private var ages = arrayOf("19","34","21","63")
    private var details = arrayOf("Beautiful","Diligent", "Hard-working", "Young")
    private val images = intArrayOf(R.drawable.siti,R.drawable.habibi,R.drawable.lili,R.drawable.shankaria)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapterMaid.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_maid_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterMaid.ViewHolder, position: Int) {
        holder.maidName.text = maids[position]
        holder.maidDetail.text = details[position]
        holder.maidAge.text = "Age : " + ages[position]
        holder.maidImage.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return maids.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var maidImage: ImageView
        var maidName: TextView
        var maidAge: TextView
        var maidDetail: TextView

        init {
            maidImage = itemView.findViewById(R.id.card_maid_image)
            maidName = itemView.findViewById(R.id.card_maid_name)
            maidAge = itemView.findViewById(R.id.card_maid_age)
            maidDetail = itemView.findViewById(R.id.card_maid_detail)

            itemView.setOnClickListener {
                val position: Int = adapterPosition

                Toast.makeText(
                    itemView.context,
                    "You clicked on ${maids[position]}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}