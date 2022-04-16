package com.example.kakitanganapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PaymentSuccessful : AppCompatActivity() {
    lateinit var successNxt: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_successful)

        val servicePrice = intent.getDoubleExtra("servicePrice", 0.0)
        val paymentType = intent.getStringExtra("paymentType")
        val dataService = intent.getStringExtra("serviceType")
        val dateTimeVal = intent.getStringExtra("appTime")
        val maidName = intent.getStringExtra("maidName")
        val maidID = intent.getIntExtra("maidID", 0)
        val userPhone = intent.getStringExtra("userPhone")
        val currTime = System.currentTimeMillis()
        val userAdd = intent.getStringExtra("userAdd")
        val userEmail = intent.getStringExtra("userEmail")//

        val currBooking = Booking(currTime,
            dataService!!, dateTimeVal!!, maidID.toString(), userAdd!!, servicePrice, userEmail!!
        );

        successNxt = findViewById(R.id.btnSuccessNxt)

        val bookingID = Firebase.database.reference.child("Booking").child(userPhone!!).push().key

        val ref1 = Firebase.database.reference.child("Booking").child(userPhone).child(bookingID.toString())
            .setValue(currBooking).addOnSuccessListener {
                Toast.makeText(this, "Booking is successful", Toast.LENGTH_SHORT).show()
            }

        val intent = Intent(this, PaymentDetail1::class.java)
        intent.putExtra("bookingID", bookingID)
        intent.putExtra("paymentType",paymentType)
        startActivity(intent)

        successNxt.setOnClickListener {
            val intent = Intent(this,PaymentDetail1::class.java)
            intent.putExtra("paymentType", paymentType)
            startActivity(intent)
        }
    }
}