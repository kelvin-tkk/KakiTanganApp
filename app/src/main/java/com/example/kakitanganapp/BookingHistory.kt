package com.example.kakitanganapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Date
import java.time.LocalDate

class BookingHistory : AppCompatActivity(){

    private lateinit var ttlHistory : Toolbar
    private lateinit var newArrayList : ArrayList<Bookings>
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<BookingAdapter.ViewHolder>? = null
    lateinit var maidImages : Array<Int>
    lateinit var bookingIds : Array<String>
    lateinit var cleaningTypes : Array<String>
    lateinit var cleaningDates : Array<Date>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_history)

        ttlHistory = findViewById(R.id.ttlHistory)
        setSupportActionBar(ttlHistory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerViewHistory)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<Bookings>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..5) {
            data.add(Bookings(R.drawable.habibi, "Normal Clean","RM100", Date(222222222222) ))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = BookingAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}