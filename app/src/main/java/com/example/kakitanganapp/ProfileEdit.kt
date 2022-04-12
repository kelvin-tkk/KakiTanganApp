package com.example.kakitanganapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class ProfileEdit : AppCompatActivity() {

    private lateinit var ttlProfileEdit : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        ttlProfileEdit = findViewById(R.id.ttlProfileEdit)
        setSupportActionBar(ttlProfileEdit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //
    }
}