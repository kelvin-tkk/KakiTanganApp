package com.example.kakitanganapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ConfirmPayment : AppCompatActivity() {
    lateinit var confirm: Button
    lateinit var cancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_payment)

        confirm = findViewById(R.id.btnConfirm)
        confirm.setOnClickListener {
            val intent = Intent(this,PaymentSuccessful::class.java)
            startActivity(intent)
        }

        cancel = findViewById(R.id.btnCancel)
        cancel.setOnClickListener {
            val intent = Intent(this,PaymentFail::class.java)
            startActivity(intent)
        }
    }
}