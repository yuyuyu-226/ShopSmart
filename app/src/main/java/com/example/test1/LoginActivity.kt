package com.example.test1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val goToRegister = findViewById<TextView>(R.id.createAccountText)

        // Login → Products
        loginBtn.setOnClickListener {
            startActivity(Intent(this, ProductsActivity::class.java))
        }

        // Login → Register
        goToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}