package com.example.kakitanganapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PaymentFail : AppCompatActivity() {
    lateinit var FTry: Button
    lateinit var FHome: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_fail)

        FTry = findViewById(R.id.btnFTry)
        FTry.setOnClickListener {
            val intent = Intent(this,UserAppointment::class.java)
            startActivity(intent)
        }

        FHome = findViewById(R.id.btnFHome)
        FHome.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }
    }
}