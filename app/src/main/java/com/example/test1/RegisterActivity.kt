package com.example.test1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private val repository = AuthRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val createBtn = findViewById<Button>(R.id.createAccountBtn)
        val goToLogin = findViewById<TextView>(R.id.loginText)

        val nameField = findViewById<EditText>(R.id.fullName)
        val emailField = findViewById<EditText>(R.id.email)
        val passField = findViewById<EditText>(R.id.password)
        val addressField = findViewById<EditText>(R.id.address)


        // Register → Login
        createBtn.setOnClickListener {
            val name = nameField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val pass = passField.text.toString().trim()
            val address = addressField.text.toString().trim()

            // Basic validation to prevent empty calls to Firebase
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Email and Password required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            repository.registerUser(name, email, pass, address) { success, error ->
                // CRITICAL: Move back to the Main Thread for UI updates
                runOnUiThread {
                    if (success) {
                        Toast.makeText(this@RegisterActivity, "Account Created!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, ProductsActivity::class.java))
                        finish() // Destroy RegisterActivity so they can't go "back" to it
                    } else {
                        // This will now catch "Wrong Input" errors from Firebase (like invalid email format)
                        Toast.makeText(this@RegisterActivity, "Error: $error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        // Text → Login
        goToLogin.setOnClickListener {
//            startActivity(Intent(this, ProductsActivity::class.java))
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}