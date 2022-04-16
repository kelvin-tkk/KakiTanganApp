package com.example.kakitanganapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Comment
import org.w3c.dom.Text
import java.sql.Date
import java.time.LocalDate

class BookingHistory : AppCompatActivity(){

    private lateinit var dbRef: DatabaseReference
    private lateinit var ttlHistory : Toolbar
    private lateinit var newArrayList : ArrayList<Bookings>
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<BookingAdapter.ViewHolder>? = null
    private lateinit var testBox : TextView
    val data = ArrayList<Booking>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_history)

        ttlHistory = findViewById(R.id.ttlHistory)
        setSupportActionBar(ttlHistory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        testBox = findViewById(R.id.testBox)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerViewHistory)
        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)
        // ArrayList of class ItemsViewModel

        val sharedPref = applicationContext.getSharedPreferences("prefKey", Context.MODE_PRIVATE)
        val userPhone = sharedPref.getString("userPhone",null)


        dbRef = Firebase.database.reference.child("Booking").child(userPhone!!)

        dbRef.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                /*
                if (dataSnapshot.childrenCount > 0) {
                    for ( ds1 :DataSnapshot in dataSnapshot.children) {
                        var singleBook = dataSnapshot.getValue<Booking>()!!
                        //data.add(Booking(singleBook!!.currBookingTime, singleBook!!.cleaningType,singleBook.cleaningDate, singleBook.maidID,singleBook.address,singleBook.price,singleBook.userEmail ))
                        var time = singleBook!!.currBookingTime
                        var type = singleBook!!.cleaningType
                        var date = singleBook!!.cleaningDate
                        var maidID = singleBook!!.maidID
                        var add = singleBook!!.address
                        var price = singleBook!!.price
                        var email = singleBook!!.userEmail
                    //data.add(Booking(time,type,date,maidID,add,price,email))
                    }
                }
                */
                    var singleBook = dataSnapshot.getValue<Booking>()!!
                    var time = singleBook!!.currBookingTime
                    var type = singleBook!!.cleaningType
                    var date = singleBook!!.cleaningDate
                    var maidID = singleBook!!.maidID
                    var add = singleBook!!.address
                    var price = singleBook!!.price
                    var email = singleBook!!.userEmail
                    data.add(Booking(time,type,date,maidID,add,price,email))

                    val adapter = BookingAdapter(data)

                    // Setting the Adapter with the recyclerview
                    recyclerview.adapter = adapter
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        });


        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..5) {
            //data.add(Booking(0, "Normal Clean","RM100", "222222222222","",0.0,"" ))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}