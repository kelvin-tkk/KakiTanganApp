package com.example.kakitanganapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class PaymentDetail1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_detail1)
    }

    var getdata = object:ValueEventListener{
        override fun onCancelled(error: DatabaseError) {
        }

        override fun onDataChange(p0: DataSnapshot) {
            var sb = StringBuilder()
            for(i in p0.children){
                var paymentType = i.child("paymentType").getValue()
                var cleaningType = i.child("cleaningType").getValue()
                var cleaningDateTime = i.child("cleaningDateTime").getValue()
                var customerName = i.child("customerName").getValue()
                var serviceAdd = i.child("serviceAdd").getValue()
                var totalPrice = i.child("totalPrice").getValue()
                sb.append("$(i.key) $paymentType $cleaningType $cleaningDateTime $customerName $serviceAdd $totalPrice")
            }
//            textView.setText(sb)
        }
    }
}