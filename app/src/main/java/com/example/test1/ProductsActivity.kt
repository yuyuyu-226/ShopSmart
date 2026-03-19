package com.example.test1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val productList = listOf(
            Product("Wireless Headphones", "Electronics", 89.99),
            Product("Smart Watch", "Electronics", 249.99),
            Product("Running Shoes", "Fashion", 129.99),
            Product("Backpack", "Accessories", 59.99),
            Product("Water Bottle", "Accessories", 24.99),
            Product("Sunglasses", "Fashion", 79.99),
            Product("Phone Case", "Electronics", 19.99),
            Product("Yoga Mat", "Sports", 39.99)
        )

        recyclerView.adapter = ProductAdapter(productList)

        // Logout → Login (CLEAR BACKSTACK)
        logoutBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}