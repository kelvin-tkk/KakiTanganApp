package com.example.kakitanganapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Payment : AppCompatActivity() {
    lateinit var payCard: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        payCard = findViewById(R.id.btnPayCard)
        payCard.setOnClickListener {
            val intent = Intent(this,CardDetail::class.java)
            startActivity(intent)
        }
    }
}