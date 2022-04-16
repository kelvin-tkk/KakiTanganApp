package com.example.kakitanganapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookingAdapter(private val bookingList: ArrayList<Booking>) : RecyclerView.Adapter<BookingAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_booking_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: BookingAdapter.ViewHolder, position: Int) {
        val currentItem = bookingList[position]
        //holder.maidImage.setImageResource(currentItem.maidImage)
        //holder.bookingId.text = currentItem.bookingId
        holder.cleaningType.text = currentItem.cleaningType
        holder.cleaningDate.text = currentItem.cleaningDate.toString()
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //val maidImage : ImageView = itemView.findViewById(R.id.card_history_maid_image)
        //val bookingId : TextView = itemView.findViewById(R.id.card_booking_id)
        val cleaningType : TextView = itemView.findViewById(R.id.card_cleaning_type)
        val cleaningDate : TextView = itemView.findViewById(R.id.card_cleaning_date)



    }
}