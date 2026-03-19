package com.example.test1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val repository = AuthRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        val viewModel = LoginViewModel(repository)

        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val goToRegister = findViewById<TextView>(R.id.createAccountText)

        val emailField = findViewById<EditText>(R.id.email)
        val passwordField = findViewById<EditText>(R.id.password)

        // Login → Products
        loginBtn.setOnClickListener {
            val email = emailField.text.toString()
            val pass = passwordField.text.toString()

            repository.login(email, pass){ isSuccess, error->
                if (isSuccess) {
                    Toast.makeText(this, "Logged In!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ProductsActivity::class.java))
                } else {
                    Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Login → Register
        goToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}