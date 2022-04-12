package com.example.kakitanganapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kakitanganapp.databinding.ActivityOurMaidBinding

class OurMaid : AppCompatActivity() {
    private lateinit var binding: ActivityOurMaidBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOurMaidBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_our_maid)

        populateMaids()

        val ourMaid = this
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = CardAdapter(maidList)
        }
    }

    private fun populateMaids(){
        val maid1 = Maid(R.drawable.ic_person,
            "Siti Azwan",
            38,
            "10 years of working experience, worked in MBPP for 5 years")
        maidList.add(maid1)

        val maid2 = Maid(R.drawable.ic_person,
            "Lim Jia Li",
            31,
            "5 years of working experience")
        maidList.add(maid2)

        maidList.add(maid1)
        maidList.add(maid2)
    }
}