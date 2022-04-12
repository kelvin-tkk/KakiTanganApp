package com.example.kakitanganapp

var maidList = mutableListOf<Maid>()

class Maid (var img: Int, var name: String, var age: Int, var description: String) {
    val id: Int? = maidList.size
}