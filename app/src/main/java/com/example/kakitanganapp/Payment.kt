package com.example.kakitanganapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import com.google.firebase.FirebaseError
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.MutableData





class Payment : AppCompatActivity() {
    lateinit var payCard: Button
    lateinit var payCOD: Button
    lateinit var paymentCT: TextView
    lateinit var paymentServiceDate: TextView
    lateinit var paymentMaidName: TextView
    lateinit var paymentServiceAddress: TextView
    lateinit var paymentPrice: TextView
    private lateinit var paymentType: String
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val dataService = intent.getStringExtra("serviceType")
        val dateTimeVal = intent.getStringExtra("appTime")
        val maidName = intent.getStringExtra("maidName")
        val maidID = intent.getIntExtra("maidID", 0)
        val servicePrice = intent.getDoubleExtra("servicePrice", 0.0)

        paymentServiceAddress = findViewById(R.id.paymentServiceAddress)
        paymentServiceDate = findViewById(R.id.paymentServiceDate)
        paymentMaidName = findViewById(R.id.paymentMaidName)
        paymentPrice = findViewById(R.id.paymentPrice)
        paymentCT = findViewById(R.id.paymentCT)
        payCard = findViewById(R.id.btnPayCard)
        payCOD = findViewById(R.id.btnCashOnD)

        val sharedPref = applicationContext.getSharedPreferences("prefKey", Context.MODE_PRIVATE)

        paymentCT.text = dataService
        paymentServiceDate.text = "Date & Time: " + dateTimeVal
        paymentMaidName.text = "Maid : " + maidName
        val userAdd = sharedPref.getString("userAddress", null)
        val userEmail = sharedPref.getString("userEmail", null)
        val userPhone = sharedPref.getString("userPhone",null)
        paymentServiceAddress.text = "Address : " + userAdd;
        paymentPrice.text = "Price : RM " + servicePrice.toString();

        val currTime = System.currentTimeMillis()
        val currBooking = Booking(currTime,
            dataService!!, dateTimeVal!!, maidID.toString(), userAdd!!, servicePrice, userEmail!!
        );


        payCard.setOnClickListener {
            paymentType = "card"
            val intent = Intent(this, CardDetail::class.java)
            intent.putExtra("servicePrice", servicePrice)
            intent.putExtra("paymentType",paymentType)
            intent.putExtra("userPhone",userPhone)
            intent.putExtra("serviceType",dataService)
            intent.putExtra("serviceTime",dateTimeVal)
            intent.putExtra("maidID",maidID)
            intent.putExtra("currTime",currTime)
            intent.putExtra("userAdd",userAdd)
            intent.putExtra("userEmail",userEmail)
            startActivity(intent)
        }
        payCOD.setOnClickListener {
            paymentType = "cod"
            val bookingID = Firebase.database.reference.child("Booking").child(userPhone!!).push().key

            val ref1 = Firebase.database.reference.child("Booking").child(userPhone).child(bookingID.toString())
                .setValue(currBooking).addOnSuccessListener {
                Toast.makeText(this, "Booking is successful", Toast.LENGTH_SHORT).show()
            }

            val intent = Intent(this, PaymentDetail1::class.java)
            intent.putExtra("bookingID", bookingID)
            intent.putExtra("paymentType",paymentType)
            startActivity(intent)


        }

    }
}