package com.empresa.gestiondegastos.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseService {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun registerUser(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                    // Guardar datos del usuario en Firestore
                    val userMap = mapOf(
                        "uid" to userId,
                        "name" to name,
                        "email" to email
                    )
                    firestore.collection("users").document(userId)
                        .set(userMap)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { e ->
                            Log.e("FirestoreError", e.message ?: "")
                            onError("Error al guardar en Firestore")
                        }
                } else {
                    onError(task.exception?.message ?: "Error al crear la cuenta")
                }
            }
    }
}
