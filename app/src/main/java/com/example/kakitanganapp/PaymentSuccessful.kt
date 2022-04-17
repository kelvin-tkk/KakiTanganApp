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

        successNxt = findViewById(R.id.btnSuccessNxt)

        val servicePrice = intent.getDoubleExtra("servicePrice", 0.0)
        val paymentType = intent.getStringExtra("paymentType")
        val dataService = intent.getStringExtra("serviceType")
        val dateTimeVal = intent.getStringExtra("serviceTime")
        val maidID = intent.getStringExtra("maidID")
        val userPhone = intent.getStringExtra("userPhone")
        val currTime = System.currentTimeMillis()
        val userAdd = intent.getStringExtra("userAdd")
        val userEmail = intent.getStringExtra("userEmail")


        val currBooking = Booking(currTime,
            dataService.toString(),
            dateTimeVal.toString(), maidID.toString(), userAdd.toString(), servicePrice, userEmail.toString()
        )

        val bookingID = Firebase.database.reference.child("Booking").child(userPhone!!).push().key

        val ref1 = Firebase.database.reference.child("Booking").child(userPhone).child(bookingID.toString())
            .setValue(currBooking).addOnSuccessListener {
                Toast.makeText(this, "Booking is successful", Toast.LENGTH_SHORT).show()
            }

        successNxt.setOnClickListener {
            val intent = Intent(this,PaymentDetail1::class.java)
            intent.putExtra("paymentType", paymentType)
            intent.putExtra("bookingID", bookingID)
            startActivity(intent)
        }
    }
}