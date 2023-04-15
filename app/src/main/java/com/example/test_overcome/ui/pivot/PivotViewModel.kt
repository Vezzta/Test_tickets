package com.example.test_overcome.ui.pivot

import androidx.lifecycle.ViewModel
import com.example.test_overcome.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PivotViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    fun currentUser(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }
}