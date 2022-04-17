package com.example.kakitanganapp

import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.*

class ConfirmPayment : AppCompatActivity() {
    lateinit var txtAmtToPay: TextView
    lateinit var txtCardNo: TextView
    lateinit var date: TextView
    lateinit var confirm: Button
    lateinit var cancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_payment)

        txtAmtToPay = findViewById(R.id.txtConfirmAmount)
        txtCardNo = findViewById(R.id.txtCardNo)
        date = findViewById(R.id.txtTransacDate)

        val servicePrice = intent.getDoubleExtra("servicePrice", 0.0)
        val paymentType = intent.getStringExtra("paymentType")
        val dataService = intent.getStringExtra("serviceType")
        val dateTimeVal = intent.getStringExtra("serviceTime")
        val maidID = intent.getStringExtra("maidID")
        val userPhone = intent.getStringExtra("userPhone")
        val userAdd = intent.getStringExtra("userAdd")
        val userEmail = intent.getStringExtra("userEmail")
        txtAmtToPay.text = "RM: " + intent.getDoubleExtra("servicePrice",0.0).toString()
        txtCardNo.text = "XXXXXXXXXXXX" + intent.getStringExtra("cardNo")
        val c: Date = Calendar.getInstance().getTime()
        println("Current time => $c")

        val df = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault())
        val formattedDate: String = df.format(c)

        date.text = formattedDate

        val sharedPref = applicationContext.getSharedPreferences("prefKey", Context.MODE_PRIVATE)

        confirm = findViewById(R.id.btnConfirm)
        confirm.setOnClickListener {
            val intent = Intent(this,PaymentSuccessful::class.java)

            intent.putExtra("servicePrice", servicePrice)
            intent.putExtra("paymentType",paymentType)
            intent.putExtra("userPhone",userPhone)
            intent.putExtra("serviceType",dataService)
            intent.putExtra("serviceTime",dateTimeVal)
            intent.putExtra("maidID",maidID)
            intent.putExtra("userAdd",userAdd)
            intent.putExtra("userEmail",userEmail)

            startActivity(intent)
        }

        cancel = findViewById(R.id.btnCancel)
        cancel.setOnClickListener {
            val intent = Intent(this,PaymentFail::class.java)
            intent.putExtra("paymentType", paymentType)
            startActivity(intent)
        }
    }
}