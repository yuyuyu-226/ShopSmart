package com.example.test1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var cardManageUsers: CardView
    private lateinit var cardAnalytics: CardView
    private lateinit var cardSettings: CardView
    private lateinit var userCount: TextView
    private lateinit var usersHeader: TextView
    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var usersLoadingBar: ProgressBar
    private lateinit var logoutBtn: Button

    private val userList = mutableListOf<User>()
    private lateinit var userAdapter: UserAdapter
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var isUsersVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        initViews()
        setupRecyclerView()
        setupClicks()
    }

    private fun initViews() {
        cardManageUsers = findViewById(R.id.cardManageUsers)
        cardAnalytics = findViewById(R.id.cardAnalytics)
        cardSettings = findViewById(R.id.cardSettings)
        userCount = findViewById(R.id.userCount)
        usersHeader = findViewById(R.id.usersHeader)
        usersRecyclerView = findViewById(R.id.usersRecyclerView)
        usersLoadingBar = findViewById(R.id.usersLoadingBar)
        logoutBtn = findViewById(R.id.logoutBtn)
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter(userList)
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        usersRecyclerView.adapter = userAdapter
    }

    private fun setupClicks() {
        logoutBtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        cardManageUsers.setOnClickListener {
            if (isUsersVisible) {
                hideUsersList()
            } else {
                showUsersList()
            }
        }

        cardAnalytics.setOnClickListener {
            Toast.makeText(this, "Analytics clicked", Toast.LENGTH_SHORT).show()
        }

        cardSettings.setOnClickListener {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showUsersList() {
        isUsersVisible = true
        usersHeader.visibility = View.VISIBLE
        usersRecyclerView.visibility = View.VISIBLE
        usersLoadingBar.visibility = View.VISIBLE
        fetchUsers()
    }

    private fun hideUsersList() {
        isUsersVisible = false
        usersHeader.visibility = View.GONE
        usersRecyclerView.visibility = View.GONE
        usersLoadingBar.visibility = View.GONE
    }

    private fun fetchUsers() {
        userList.clear()
        userAdapter.notifyDataSetChanged()

        db.collection("users").get()
            .addOnSuccessListener { result ->
                usersLoadingBar.visibility = View.GONE
                for (doc in result) {
                    val user = doc.toObject(User::class.java)
                    userList.add(user)
                }
                userAdapter.notifyDataSetChanged()
                userCount.text = "${userList.size} users"
            }
            .addOnFailureListener { e ->
                usersLoadingBar.visibility = View.GONE
                Toast.makeText(this, "Failed to load users: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
