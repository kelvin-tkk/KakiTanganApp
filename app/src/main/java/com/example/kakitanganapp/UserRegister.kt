package com.example.kakitanganapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kakitanganapp.databinding.ActivityUserRegisterBinding
import com.google.firebase.firestore.FirebaseFirestore

class UserRegister : AppCompatActivity() {

    private lateinit var binding: ActivityUserRegisterBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()        //singleton

        binding.btnRegUser.setOnClickListener {
            var phoneNum = binding.editRegPhone.text.toString()
            val name = binding.editRegName.text.toString()
//            val gender = binding.radGroupGender.text.toString()
            val address = binding
            val password = binding.editRegPassword.text.toString()
        }
    }
}