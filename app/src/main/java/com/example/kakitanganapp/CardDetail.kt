package com.example.kakitanganapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class CardDetail : AppCompatActivity() {
    private lateinit var makeCardPay: Button
    private lateinit var txtAmtToPay: TextView

    private lateinit var txtToPayAmount : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)

        txtAmtToPay = findViewById(R.id.txtToPayAmount)
        txtAmtToPay.text = intent.getDoubleExtra("servicePrice",0.0).toString()

        makeCardPay = findViewById(R.id.btnCardPay)
        makeCardPay.setOnClickListener {
            val intent = Intent(this,ConfirmPayment::class.java)
            startActivity(intent)
        }
    }
}