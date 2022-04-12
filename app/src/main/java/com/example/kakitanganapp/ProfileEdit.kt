package com.example.kakitanganapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class ProfileEdit : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnEPSave : Button
    private lateinit var ttlProfileEdit : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        ttlProfileEdit = findViewById(R.id.ttlProfileEdit)
        setSupportActionBar(ttlProfileEdit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btnEPSave= findViewById(R.id.btnEPSave)
        btnEPSave.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnEPSave->{
                //If successful
                Toast.makeText(
                    this,
                    "Success saved changes",
                    Toast.LENGTH_LONG
                ).show()
                startActivity(
                    Intent(
                        this,
                        UserProfile::class.java
                    )
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}