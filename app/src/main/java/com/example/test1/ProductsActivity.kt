package com.example.test1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProductsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var logoutBtn: Button
    private lateinit var cartItemsText: TextView
    private lateinit var totalPriceText: TextView
    private val productList = mutableListOf<Product>()
    private lateinit var adapter: ProductAdapter
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        // Views
        recyclerView = findViewById(R.id.recyclerView)
        logoutBtn = findViewById(R.id.logoutBtn)
        cartItemsText = findViewById(R.id.cartItems)
        totalPriceText = findViewById(R.id.totalPrice)

        // RecyclerView setup
        adapter = ProductAdapter(productList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Fetch products from Firestore
        fetchProducts()

        // Logout functionality
        logoutBtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun fetchProducts() {
        db.collection("products").get()
            .addOnSuccessListener { result ->
                productList.clear()
                for (doc in result) {
                    val product = doc.toObject(Product::class.java)
                    productList.add(product)
                }
                adapter.notifyDataSetChanged()
                updateCartSummary() // optional, if you want
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load products: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    // Optional: Update bottom bar cart summary
    private fun updateCartSummary() {
        // Example: simple summary (modify when cart logic implemented)
        val totalItems = productList.size
        val totalPrice = productList.sumOf { it.price }
        cartItemsText.text = "$totalItems Items"
        totalPriceText.text = "$${String.format("%.2f", totalPrice)}"
    }
}