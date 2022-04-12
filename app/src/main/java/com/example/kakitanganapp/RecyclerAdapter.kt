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

class RecyclerAdapter(val context: Context): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var titles = arrayOf("Normal Clean","Deep Clean","Disinfection")
    private var details = arrayOf("RM 45/ hour","RM 75/ hour", "RM 70/ hour")
    private val images = intArrayOf(R.drawable.cleaning_pic,R.drawable.cleaning_pic,R.drawable.cleaning_pic)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDetail.text = details[position]
        holder.itemImage.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init{
            itemImage = itemView.findViewById(R.id.card_item_image)
            itemTitle = itemView.findViewById(R.id.card_item_title)
            itemDetail = itemView.findViewById(R.id.card_item_detail)

            itemView.setOnClickListener{
                val position: Int = adapterPosition

                Toast.makeText(itemView.context, "You clicked on ${titles[position]}", Toast.LENGTH_LONG).show()

                val intent = Intent(context, MaidList::class.java)
                context.startActivity(intent)
            }
        }
    }
}

