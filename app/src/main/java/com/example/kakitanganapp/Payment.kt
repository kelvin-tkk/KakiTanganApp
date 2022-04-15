package com.example.kakitanganapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Payment : AppCompatActivity() {
    lateinit var payCard: Button
    lateinit var payCOD: Button
    lateinit var paymentCT: TextView
    lateinit var paymentServiceDate: TextView
    lateinit var paymentMaidName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val dataService = intent.getStringExtra("serviceType")
        val dateTimeVal = intent.getStringExtra("appTime")
        val maidName = intent.getStringExtra("maidName")
        val maidID = intent.getIntExtra("maidID", 0)

        paymentServiceDate = findViewById(R.id.paymentServiceDate)
        paymentMaidName = findViewById(R.id.paymentMaidName)
        paymentCT = findViewById(R.id.paymentCT)
        payCard = findViewById(R.id.btnPayCard)
        payCOD = findViewById(R.id.btnCashOnD)

        paymentCT.text=dataService
        paymentServiceDate.text = dateTimeVal
        paymentMaidName.text = maidName

        payCard.setOnClickListener {
            val intent = Intent(this,CardDetail::class.java)
            startActivity(intent)
        }
        payCOD.setOnClickListener{
            val intent = Intent(this,PaymentDetail1::class.java)
            startActivity(intent)
        }
    }
}