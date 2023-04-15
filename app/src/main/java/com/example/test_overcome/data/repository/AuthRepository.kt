package com.example.test_overcome.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun createUserWithEmailAndPassword(email: String, pass: String): Task<AuthResult>
    fun signInWithEmailAndPassword(email: String, pass: String): Task<AuthResult>
    fun getCurrentUser(): FirebaseUser?
    fun signOut()
    fun updateUsername(username: String)
}
