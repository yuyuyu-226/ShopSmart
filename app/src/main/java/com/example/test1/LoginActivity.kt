package com.example.test1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private val repository = AuthRepository()
    private lateinit var loginBtn: Button
    private lateinit var loadingBar: ProgressBar
    private var isLoggingIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn = findViewById(R.id.loginBtn)
        loadingBar = findViewById(R.id.loadingBar)
        val goToRegister = findViewById<TextView>(R.id.createAccountText)
        val emailField = findViewById<EditText>(R.id.email)
        val passwordField = findViewById<EditText>(R.id.password)

        loginBtn.setOnClickListener {
            if (isLoggingIn) return@setOnClickListener

            val email = emailField.text.toString()
            val pass = passwordField.text.toString()

            if (email.isBlank() || pass.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            setLoading(true)

            repository.login(email, pass) { isSuccess, error, role ->
                runOnUiThread {
                    setLoading(false)
                    if (isSuccess) {
                        Toast.makeText(this, "Logged In!", Toast.LENGTH_SHORT).show()
                        val destination = if (role == "admin") AdminDashboardActivity::class.java else ProductsActivity::class.java
                        startActivity(Intent(this, destination))
                        finish()
                    } else {
                        Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        goToRegister.setOnClickListener {
//            startActivity(Intent(this, AdminDashboardActivity::class.java))
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun setLoading(loading: Boolean) {
        isLoggingIn = loading
        loginBtn.isEnabled = !loading
        loginBtn.text = if (loading) "Logging in..." else "Log In"
        loadingBar.visibility = if (loading) View.VISIBLE else View.GONE
    }
}