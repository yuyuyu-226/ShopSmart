package com.example.test1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class ProductsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var logoutBtn: Button
    private lateinit var cartItemsText: TextView
    private lateinit var totalPriceText: TextView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private val productList = mutableListOf<Product>()
    private lateinit var adapter: ProductAdapter
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_products)

        // Views
        recyclerView = findViewById(R.id.recyclerView)
        logoutBtn = findViewById(R.id.logoutBtn)
        cartItemsText = findViewById(R.id.cartItems)
        totalPriceText = findViewById(R.id.totalPrice)
        swipeRefresh = findViewById(R.id.swipeRefresh)

        // RecyclerView setup
        adapter = ProductAdapter(productList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // SwipeRefresh setup
        swipeRefresh.setOnRefreshListener {
            fetchProducts()
        }

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
                val oldSize = productList.size
                productList.clear()
                for (doc in result) {
                    try {
                        val product = doc.toObject(Product::class.java)
                        productList.add(product)
                    }catch (e: Exception){
                        Log.e("ProductsFetchErr",e.message.toString())
                    }
                }

                Toast.makeText(
                    this,
                    "Loaded: ${result.size()}",
                    Toast.LENGTH_LONG
                ).show()

                // Efficient RecyclerView update
                if (oldSize > 0) {
                    adapter.notifyItemRangeRemoved(0, oldSize)
                }
                adapter.notifyItemRangeInserted(0, productList.size)

                updateCartSummary()
                swipeRefresh.isRefreshing = false
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Failed to load products: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                swipeRefresh.isRefreshing = false
            }
    }

    // Update bottom bar cart summary
    private fun updateCartSummary() {
        val totalItems = productList.size
        val totalPrice = productList.sumOf { it.price }
        cartItemsText.text = String.format(Locale.US, "%d Items", totalItems)
        totalPriceText.text = String.format(Locale.US, "$%.2f", totalPrice)
    }
}