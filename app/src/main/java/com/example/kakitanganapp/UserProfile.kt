package com.example.kakitanganapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class UserProfile : AppCompatActivity(), View.OnClickListener {

    lateinit var btnEditProf : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

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
}