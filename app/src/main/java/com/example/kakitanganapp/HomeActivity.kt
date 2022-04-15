package com.example.kakitanganapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity(), View.OnClickListener , NavigationView.OnNavigationItemSelectedListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var currUser: User
    private lateinit var toolbar: Toolbar
    private lateinit var drawer : DrawerLayout
    private lateinit var btnBookNow : Button
    private lateinit var btnLogout : CardView
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnBookNow = findViewById(R.id.btnBookNow)
        btnBookNow.setOnClickListener(this)

        btnLogout = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser
        if(currentUser ==null){
            startActivity(
                Intent(this,UserLogin::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finish()
        }
        else{
            sharedPref = applicationContext.getSharedPreferences("prefKey", Context.MODE_PRIVATE)
            val userRef = Firebase.database.reference.child("User").child(auth.uid.toString())
            userRef.get().addOnSuccessListener {

                currUser = it.getValue<User>()!!

                //Initialising the Editor
                editor = sharedPref.edit()
                editor.putString("userName", currUser.name) // Storing string
                editor.putString("userEmail", currUser.email) // Storing string
                editor.putString("userAddress", currUser.address) // Storing string
                editor.commit() // commit changes

            }.addOnFailureListener {
                Toast.makeText(this, "Failure to retrieve session", Toast.LENGTH_LONG).show()
                startActivity (
                    Intent(
                        this, UserLogin::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }

        toolbar = findViewById(R.id.toolbarHome)
        drawer = findViewById(R.id.drawer_layout)
        val navigationView : NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle : ActionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_profile -> {
                startActivity(
                    Intent(
                        this,
                        UserProfile::class.java
                    )
                )
            }
            R.id.nav_home -> {
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.nav_booking_history -> {
                startActivity(
                    Intent(
                        this,
                        BookingHistory::class.java
                    )
                )
            }
        }
        return true
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnBookNow -> {
                startActivity(
                    Intent(
                        this,
                        UserAppointment::class.java
                    )
                )
            }
            R.id.btnLogout -> {
                val sharedPref = getSharedPreferences("prefKey", Context.MODE_PRIVATE)
                editor.remove("userName"); // will delete key name
                editor.remove("userEmail"); // will delete key email
                editor.remove("userAddress"); // will delete key add

                editor.commit(); // commit changes
                //Following will clear all the data from shared preferences
                editor.clear();
                editor.commit(); // commit changes
                with(sharedPref.edit()) {
                    putString("role", "")
                    apply()
                }
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(
                    this,
                    "Logout successfully",
                    Toast.LENGTH_LONG
                ).show()
                startActivity(
                    Intent(
                        this,
                        UserLogin::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                finish()
            }
        }
    }
}