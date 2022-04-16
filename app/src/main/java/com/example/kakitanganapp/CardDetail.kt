package com.example.kakitanganapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class CardDetail : AppCompatActivity(){
    lateinit var txtAmtToPay: TextView
    private lateinit var editCardHolderName: EditText
    private lateinit var editCardNo : EditText
    private lateinit var spinMonth : Spinner
    private lateinit var spinYear : Spinner
    private lateinit var editCVV : EditText
    private lateinit var btnCardPay: Button

    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)
        spinMonth = findViewById(R.id.spinMonth)
        val months = Array(12) {i -> (i + 1).toString()}
        val adapter = ArrayAdapter<CharSequence>(this,R.layout.spinner_text,months)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown)
        spinMonth.adapter = adapter
        spinMonth.setSelection(Calendar.getInstance().get(Calendar.MONTH)+1)
        spinYear = findViewById(R.id.spinYear)
        val years = Array(5) { i -> (Calendar.getInstance().get(Calendar.YEAR) + i).toString() }
        val adapter1 = ArrayAdapter<CharSequence>(this,R.layout.spinner_text,years)
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown)
        spinYear.adapter = adapter1

        editCardHolderName = findViewById(R.id.editCardHolderName)
        editCardNo = findViewById(R.id.editCardNo)
        editCVV = findViewById(R.id.editCVV)
        btnCardPay = findViewById(R.id.btnCardPay)
        txtAmtToPay = findViewById(R.id.txtToPayAmount)

        val servicePrice = intent.getDoubleExtra("servicePrice", 0.0)
        val paymentType = intent.getStringExtra("paymentType")
        val dataService = intent.getStringExtra("serviceType")
        val dateTimeVal = intent.getStringExtra("appTime")
        val maidName = intent.getStringExtra("maidName")
        val maidID = intent.getIntExtra("maidID", 0)
        val userPhone = intent.getStringExtra("userPhone")
        val currTime = intent.getLongExtra("currTime",0)
        val userAdd = intent.getStringExtra("userAdd")
        val userEmail = intent.getStringExtra("userEmail")

        txtAmtToPay.text = servicePrice.toString()

        val sharedPref = applicationContext.getSharedPreferences("prefKey", Context.MODE_PRIVATE)

        btnCardPay.setOnClickListener{

            val name = editCardHolderName.text.toString().trim()
            val cardNo = editCardNo.text.toString().trim()
            val masterCard = Pattern.compile("^5[1-5][0-9]{14,14}\$")
            val mc = masterCard.matcher(cardNo)
            val visa = Pattern.compile("^4(53[29][0-9]{12,12}|556[0-9]{12,12}|916[0-9]{12,12}|929[0-9]{12,12}|485[0-9]{12,12}|716[0-9]{12,12}|[0-9]{15,15}|0240071[0-9]{8,8})\$")
            val vs = visa.matcher(cardNo)
            val cvv = editCVV.text.toString().trim()
            var expMonth = spinMonth.selectedItem.toString()
            if(expMonth.length == 1)
                expMonth = "0$expMonth"
            val expYear = spinYear.selectedItem.toString()
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val strDate = sdf.parse("01/$expMonth/$expYear")

            if(TextUtils.isEmpty(name)) {
                editCardHolderName.error = "Enter card holder name"
                editCardHolderName.requestFocus()

            }else if(!mc.matches() && !vs.matches()){
                editCardNo.error = "Enter valid card number"
                editCardNo.requestFocus()
            }else if(System.currentTimeMillis() > strDate.time){
                Toast.makeText(
                    this,
                    "Enter valid expiry date",
                    Toast.LENGTH_LONG
                ).show()
                spinMonth.requestFocus()
            }else if(cvv.length != 3){
                editCVV.error = "Enter valid CVV"
                editCVV.requestFocus()
            }else{
                /*val rnd = Random()
                val otp = rnd.nextInt(999999)
                val otpString = String.format("%06d", otp)
                val sender = Thread {
                    try {
                        val sender = GMailSender("bookj-pm19@student.tarc.edu.my", "010127070321")
                        sender.sendMail(
                            "KakiTangan - OTP",
                            "Your OTP for donation is $otpString.",
                            "bookj-pm19@student.tarc.edu.my",
                            email
                        )
                    } catch (e: Exception) {
                    }
                }
                sender.start()*/
                val intent = Intent(this, ConfirmPayment::class.java)
                intent.putExtra("cardNo", cardNo.substring(12))
                /*intent.putExtra("otp",otpString)*/
                intent.putExtra("servicePrice", servicePrice)
                intent.putExtra("paymentType",paymentType)
                intent.putExtra("userPhone",userPhone)
                intent.putExtra("serviceType",dataService)
                intent.putExtra("serviceTime",dateTimeVal)
                intent.putExtra("maidID",maidID.toString())
                intent.putExtra("currTime",currTime)
                intent.putExtra("userAdd",userAdd)
                intent.putExtra("userEmail",userEmail)
                startActivity(intent)
            }
        }
    }
}