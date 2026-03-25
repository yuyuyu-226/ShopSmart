package com.example.test1

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository(private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()) {

    // Helper to get current user
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    fun login(email: String, pass: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null) // Success!
                } else {
                    onResult(false, task.exception?.message) // Fail with error
                }
            }
    }

    fun registerUser(fullName: String, email: String, pass: String, address: String,  onResult: (Boolean, String?) -> Unit) {
        // Step 1: Create the Auth Account
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = task.result?.user?.uid ?: ""

                    // Step 2: Create the Database Profile with your constraints
                    val userProfile = hashMapOf(
                        "id" to userId,                     // PK
                        "first_name" to fullName,           // Placeholder for now
                        "last_name" to "",                  // TBA
                        "email" to email,                   // Unique by Auth design
                        "address" to address,
                        "role" to "buyer",                  // Default role
                        "profile_image" to "",              // TBA
                        "created_at" to FieldValue.serverTimestamp(),
                        "updated_at" to FieldValue.serverTimestamp()
                    )

                    db.collection("users").document(userId)
                        .set(userProfile)
                        .addOnSuccessListener {
                            onResult(true, null)
                        }
                        .addOnFailureListener { e ->
                            onResult(false, e.message)
                        }
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun logout() = firebaseAuth.signOut()
}