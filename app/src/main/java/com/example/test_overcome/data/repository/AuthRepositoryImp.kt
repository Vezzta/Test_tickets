package com.example.test_overcome.data.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {
    override fun createUserWithEmailAndPassword(email: String, pass: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, pass)
    }

    override fun signInWithEmailAndPassword(email: String, pass: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, pass)
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun updateUsername(username: String) {
        val user = firebaseAuth.currentUser

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .build()

        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TEST", "User profile updated.")
                }
            }
    }
}