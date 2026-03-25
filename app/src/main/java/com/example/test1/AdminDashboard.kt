package com.example.test1

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var cardManageUsers: CardView
    private lateinit var cardAnalytics: CardView
    private lateinit var cardSettings: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        // Initialize views
        cardManageUsers = findViewById(R.id.cardManageUsers)
        cardAnalytics = findViewById(R.id.cardAnalytics)
        cardSettings = findViewById(R.id.cardSettings)

        setupClicks()
    }

    private fun setupClicks() {
        cardManageUsers.setOnClickListener {
            Toast.makeText(this, "Manage Users clicked", Toast.LENGTH_SHORT).show()
        }

        cardAnalytics.setOnClickListener {
            Toast.makeText(this, "Analytics clicked", Toast.LENGTH_SHORT).show()
        }

        cardSettings.setOnClickListener {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
        }
    }
}