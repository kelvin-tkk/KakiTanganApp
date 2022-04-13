package com.example.kakitanganapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CardDetail : AppCompatActivity() {
    lateinit var makeDonate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)

        makeDonate = findViewById(R.id.btnMakeDonate)
        makeDonate.setOnClickListener {
            val intent = Intent(this,ConfirmPayment::class.java)
            startActivity(intent)
        }
    }
}