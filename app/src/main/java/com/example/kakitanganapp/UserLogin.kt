package com.example.kakitanganapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class UserLogin : AppCompatActivity(), TextView.OnEditorActionListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var btnLogin : Button
    private lateinit var editLoginEmail : EditText
    private lateinit var editLoginPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        auth = FirebaseAuth.getInstance()
        val loginUser = auth.currentUser
        if (loginUser != null) {
            startActivity(
                Intent(
                    this, HomeActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finish()
        }

        btnLogin = findViewById(R.id.btnLogin)
        editLoginEmail = findViewById(R.id.editLoginEmail)
        editLoginPassword = findViewById(R.id.editLoginPassword)
        editLoginPassword.setOnEditorActionListener(this)

        btnLogin.setOnClickListener{
            val userEmail = editLoginEmail.text.toString().trim()
            val userPass = editLoginPassword.text.toString()

            if(TextUtils.isEmpty(userEmail)|| !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                editLoginEmail.error = "Invalid email entered"
                editLoginEmail.requestFocus()
            }else if (userPass.length < 8){
                editLoginPassword.error = "Password not less than 8 digits"
                editLoginPassword.requestFocus()
            }else{
                auth.signInWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(this){
                    task -> if (task.isSuccessful){
                        val user = auth.currentUser
                        if(user != null){
                            if(user.isEmailVerified){
                                val userRef = Firebase.database.reference.child("User").child(auth.uid.toString())
                                userRef.get().addOnSuccessListener {
                                    val user = it.getValue<User>()
                                    val sharedPref = getSharedPreferences(
                                        "prefKey", Context.MODE_PRIVATE
                                    )
                                    Toast.makeText(
                                        this,"Login successfully",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    startActivity(
                                        Intent(this,HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    )
                                    finish()
                                }
                            }else{
                                user.sendEmailVerification()
                                Toast.makeText(
                                    this,
                                    "Verification has been sent to your email",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }else{
                        Toast.makeText(
                            this,"Invalid email or password",
                            Toast.LENGTH_LONG
                        ).show()
                    editLoginEmail.setText("")
                    editLoginEmail.requestFocus()
                    editLoginPassword.setText("")
                    }
                }
            }
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean{
        when(v?.id){
            R.id.editLoginPassword->{
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val imm: InputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
                    btnLogin.performClick()
                    return true
                }
            }
        }
        return false
    }
}