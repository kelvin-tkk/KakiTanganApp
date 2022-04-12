package com.example.kakitanganapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import java.sql.Date
import java.time.LocalDate

class BookingHistory : AppCompatActivity() {

    private lateinit var newRecyclerView : RecyclerView
    private lateinit var newArrayList : ArrayList<Bookings>
    lateinit var maidImages : Array<Int>
    lateinit var bookingIds : Array<String>
    lateinit var cleaningTypes : Array<String>
    lateinit var cleaningDates : Array<Date>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_history)



        maidImages = arrayOf(
            R.drawable.habibi,
            R.drawable.habibi,
            R.drawable.habibi
        )

        bookingIds = arrayOf(
            "123","124","1256"
        )

        cleaningTypes = arrayOf(
            "Deep","Normal","Disinfection"
        )

        cleaningDates = arrayOf(
            Date(222222222),
            Date(2222222222),
            Date(22222222222)
        )
    }

    private fun getUserData(){
        for(i in maidImages.indices){
            val bookings = Bookings(maidImages[i],bookingIds[i],cleaningTypes[i],cleaningDates[i])
            newArrayList.add(bookings)
        }

        newRecyclerView.adapter = BookingAdapter(newArrayList)
    }
}