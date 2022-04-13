package com.example.kakitanganapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class PasswordRetrieve : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var ttlPR : Toolbar
    private lateinit var editPREmail : EditText
    private lateinit var btnPassReset : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_retrieve)

        ttlPR = findViewById(R.id.ttlPR)
        setSupportActionBar(ttlPR)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        editPREmail = findViewById(R.id.editPREmail)
        editPREmail.setOnEditorActionListener{_, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken,0)
                true
            }else
                false
        }

        btnPassReset = findViewById(R.id.btnPassReset)
        btnPassReset.setOnClickListener{
            val email = editPREmail.text.toString().trim()

            if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                editPREmail.error = "Please your email"
                editPREmail.requestFocus()
            }else{
                auth = FirebaseAuth.getInstance()
                auth.sendPasswordResetEmail(email).addOnCompleteListener(this, OnCompleteListener { task->
                    if(task.isSuccessful){
                        Toast.makeText(
                            this,"The email for password reset link has been sent", Toast.LENGTH_LONG
                        ).show()
                        startActivity(Intent(this,UserLogin::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        finish()
                    }else{
                        Toast.makeText(
                            this,"There is no account registered with this email", Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}