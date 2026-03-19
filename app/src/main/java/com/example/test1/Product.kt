package com.example.test1

data class Product(
    val name: String,
    val category: String,
    val price: Double,
    var quantity: Int = 0
)