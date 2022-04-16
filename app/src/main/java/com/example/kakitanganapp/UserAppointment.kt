package com.example.kakitanganapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateFormat
import android.util.Patterns
import android.view.MenuItem
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import java.time.format.DateTimeFormatter
import java.util.*
import android.widget.TimePicker
import android.widget.TimePicker.OnTimeChangedListener
import androidx.appcompat.widget.Toolbar


class UserAppointment : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener  {
    private lateinit var tlbAppointment : Toolbar
    lateinit var textView: TextView
    lateinit var button: Button
    lateinit var buttonNext: Button
    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0
    private lateinit var textViewDateTime : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_appointment)

        tlbAppointment = findViewById(R.id.tlbAppointment)
        setSupportActionBar(tlbAppointment)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        textView = findViewById(R.id.textView_datetime)
        button = findViewById(R.id.btnPick)
        buttonNext = findViewById(R.id.button_next)
        button.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this@UserAppointment, this@UserAppointment, year, month, day)
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + 86400000)
            datePickerDialog.show()
        }
        buttonNext.setOnClickListener {

            textViewDateTime = findViewById(R.id.textView_datetime)

            if(TextUtils.isEmpty(textViewDateTime.text)) {
                Toast.makeText(
                    this, "Invalid date and time",
                    Toast.LENGTH_LONG
                ).show()
            }else {
                val intent = Intent(this, Service::class.java)
                intent.putExtra("appTime", textViewDateTime.text)
                startActivity(intent)
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month + 1
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this@UserAppointment, this@UserAppointment, hour, minute,
            DateFormat.is24HourFormat(this))
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        OnTimeChangedListener { timePicker, hourOfDay, minute ->
            if (hourOfDay < 8)
                hour = 8
            else if (hourOfDay > 16)
                hour = 16
        }
        textView.text = "" + myDay + "/" + myMonth + "/" + myYear + " " + myHour + ":" + myMinute
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}