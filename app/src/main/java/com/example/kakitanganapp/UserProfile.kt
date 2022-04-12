package com.example.kakitanganapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class UserProfile : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    lateinit var btnEditProf : Button
    private lateinit var ttlUserProfile : Toolbar

    private lateinit var currUser : User
    private lateinit var imageViewProfile : ImageView
    private lateinit var  textProfName : TextView
    private lateinit var txtProfileGender : TextView
    private lateinit var textProfilePhoneNo: TextView
    private lateinit var textProfileEmail: TextView
    private lateinit var textProfileAddress : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        ttlUserProfile = findViewById(R.id.ttlUserProfile)
        setSupportActionBar(ttlUserProfile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btnEditProf = findViewById(R.id.btnEditProf)
        btnEditProf.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()
        var loginUser = auth.currentUser
        if(loginUser == null){
            startActivity(
                Intent(
                    this,UserLogin::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finish()
        }

        imageViewProfile = findViewById(R.id.imageViewProfile)
        textProfName = findViewById(R.id.textProfName)
        txtProfileGender = findViewById(R.id.txtProfileGender)
        textProfilePhoneNo = findViewById(R.id.textProfilePhoneNo)
        textProfileEmail = findViewById(R.id.textProfileEmail)
        textProfileAddress = findViewById(R.id.textProfileAddress)

        val userRef = Firebase.database.reference.child("User").child(auth.uid.toString())
        userRef.get().addOnSuccessListener {
            currUser = it.getValue<User>()!!
            textProfName.text = currUser.name
            txtProfileGender.text = currUser.gender
            textProfilePhoneNo.text = currUser.phoneNum
            textProfileEmail.text = currUser.email
            textProfileAddress.text = currUser.address
        }.addOnFailureListener{
            Toast.makeText(this, "Failure to login, please try again", Toast.LENGTH_SHORT).show()
            startActivity(
                Intent(
                    this,UserLogin:: class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
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