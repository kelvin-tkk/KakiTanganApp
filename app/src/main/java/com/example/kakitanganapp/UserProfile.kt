package com.example.kakitanganapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

class UserProfile : AppCompatActivity(), View.OnClickListener {

    lateinit var btnEditProf : Button
    private lateinit var ttlUserProfile : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        ttlUserProfile = findViewById(R.id.ttlUserProfile)
        setSupportActionBar(ttlUserProfile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btnEditProf = findViewById(R.id.btnEditProf)
        btnEditProf.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnEditProf->{
                startActivity(
                    Intent(
                        this,
                        ProfileEdit::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
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