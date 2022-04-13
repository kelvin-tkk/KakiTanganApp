package com.example.kakitanganapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PaymentSuccessful : AppCompatActivity() {
    lateinit var successNxt: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_successful)

        successNxt = findViewById(R.id.btnSuccessNxt)
        successNxt.setOnClickListener {
            val intent = Intent(this,PaymentDetail1::class.java)
            startActivity(intent)
        }
    }
}