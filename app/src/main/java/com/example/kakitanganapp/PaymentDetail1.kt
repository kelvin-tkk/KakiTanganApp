package com.example.kakitanganapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class PaymentDetail1 : AppCompatActivity() {

    private lateinit var btnConPayDetail: Button
    private lateinit var paymentCT: TextView
    private lateinit var paymentServiceDateTime: TextView
    private lateinit var paymentCusName: TextView
    private lateinit var paymentServiceAddress: TextView
    private lateinit var paymentPrice: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var completeBooking: Booking
    private lateinit var paymentSuccessful: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_detail1)

        val paymentType = intent.getStringExtra("paymentType")
        val bookingID =  intent.getStringExtra("bookingID")
        paymentCT = findViewById(R.id.paymentCT)
        paymentServiceAddress = findViewById(R.id.paymentServiceAddress)
        paymentCusName = findViewById(R.id.paymentCusName)
        paymentPrice = findViewById(R.id.paymentPrice)
        paymentServiceDateTime = findViewById(R.id.paymentServiceDateTime)
        paymentSuccessful = findViewById(R.id.paymentSuccessful)
        btnConPayDetail = findViewById(R.id.btnConPayDetail)

        if(paymentType.equals("cod")){
            paymentSuccessful.text = "Cash On Delivery"
        }
        else if(paymentType.equals("card")){
            paymentSuccessful.text = "Credit Card"
        }

        val sharedPref = applicationContext.getSharedPreferences("prefKey", Context.MODE_PRIVATE)
        val userPhone = sharedPref.getString("userPhone",null)

        dbRef = Firebase.database.reference.child("Booking").child(userPhone!!).child(bookingID!!)
        dbRef.get().addOnSuccessListener {

            completeBooking = it.getValue<Booking>()!!
            paymentCT.text = "Service: " + completeBooking.cleaningType
            paymentServiceDateTime.text = "Time: " + completeBooking.cleaningDate
            paymentServiceAddress.text = "Address: " + completeBooking.address
            paymentPrice.text = "Price: RM " + completeBooking.price.toString()
            paymentCusName.text = "Name: " + sharedPref.getString("userName", null)
        }.addOnFailureListener{
            Toast.makeText(this, "Unexpected error occured, please try again", Toast.LENGTH_SHORT).show()
            startActivity(
                Intent(
                    this,UserLogin:: class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }

        btnConPayDetail.setOnClickListener{
            startActivity(
                Intent(
                    this,UserLogin:: class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finish()
        }
    }


}