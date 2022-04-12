package com.example.kakitanganapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class AccRegister : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var editRegEmail: EditText
    private lateinit var editRegName: EditText
    private lateinit var radGroupGender: RadioGroup
    private lateinit var editRegPhone: EditText
    private lateinit var editRegPassword: EditText
    private lateinit var editRegConPassword: EditText
    private lateinit var editRegAddress: EditText
    private lateinit var btnRegUser: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acc_register)

        auth = Firebase.auth
        var newUser = auth.currentUser
        if (newUser != null){
            startActivity(
                Intent(
                    this, UserProfile::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finish()
        }

        //Set referencce for component
        btnRegUser = findViewById(R.id.btnRegUser)
        editRegEmail = findViewById(R.id.editRegEmail)
        editRegName = findViewById(R.id.editRegName)
        radGroupGender = findViewById(R.id.radGroupGender)
        editRegPhone = findViewById(R.id.editRegPhone)
        editRegPassword = findViewById(R.id.editRegPassword)
        editRegConPassword = findViewById(R.id.editRegConPassword)
        editRegAddress = findViewById(R.id.editRegAddress)
        editRegConPassword.setOnEditorActionListener {_, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                val imm : InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
                btnRegUser.performClick()
                true
            }else
                false
        }

        btnRegUser.setOnClickListener{
            val userName = editRegName.text.toString().trim()
            val userPhone = editRegPhone.text.toString().trim()
            val userEmail = editRegEmail.text.toString().trim()
            val userPass = editRegPassword.text.toString()
            val userAddress = editRegAddress.text.toString().trim()
            val userConPassword = editRegConPassword.text.toString()
            val mobileNumFormat =
                Pattern.compile("^(01)(([02-46-9]-*[0-9]{7,7})|([1]-*[0-9]{8,8}))\$")
            val m = mobileNumFormat.matcher(userPhone)

            if (TextUtils.isEmpty(userName)) {
                editRegName.error = "Full name cannot be empty"
                editRegName.requestFocus()
            } else if (TextUtils.isEmpty(userPhone) || !m.matches()) {
                editRegPhone.error = "Please enter valid Phone Nonumber"
                editRegPhone.requestFocus()
            } else if (TextUtils.isEmpty(userEmail) || !Patterns.EMAIL_ADDRESS.matcher(userEmail)
                    .matches()
            ) {
                editRegEmail.error = "Please enter valid email"
                editRegEmail.requestFocus()
            } else if (userPass.length < 8) {
                editRegPassword.error = "Password cannot be shorter than 8"
                editRegPassword.requestFocus()
            } else if (TextUtils.isEmpty(userConPassword) || userConPassword != userPass) {
                editRegConPassword.error = "Incorrect Password"
                editRegConPassword.requestFocus()
            }else {
                var userGender = "Male"
                when (radGroupGender.checkedRadioButtonId) {
                    R.id.radMale -> userGender = "Male"
                    R.id.radFemale -> userGender = "Female"
                }
                val newUser = User(userEmail, userPhone, userName, userGender, userAddress, userPass)
                auth.createUserWithEmailAndPassword(userEmail, userPass)
                    .addOnCompleteListener(this, OnCompleteListener{ task ->
                        if (task.isSuccessful) { //check if user is registered
                            val userRef = Firebase.database.reference.child("User").child(auth.uid.toString())
                            userRef.setValue(newUser).addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "You account has been registered.",
                                    Toast.LENGTH_LONG
                                ).show()
                                startActivity(
                                    Intent(
                                        this,
                                        UserLogin::class.java
                                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                )
                                finish()

                                FirebaseAuth.getInstance().signOut()
                            }.addOnFailureListener {
                                Toast.makeText(
                                    this,
                                    task.exception.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this,
                                task.exception.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                            editRegEmail.error = "Email registered"
                            editRegEmail.requestFocus()
                        }
                    })

            }
        }
    }
}