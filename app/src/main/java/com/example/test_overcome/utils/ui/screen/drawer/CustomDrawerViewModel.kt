package com.example.test_overcome.utils.ui.screen.drawer

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.test_overcome.data.repository.AuthRepository
import com.example.test_overcome.ui.navigation.Screens
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CustomDrawerViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel(){

    private val _uIState = MutableStateFlow(UIState())
    val uIState: StateFlow<UIState> = _uIState

    fun setVisibleDialog(it: Boolean){
        _uIState.update { state ->
            state.copy(visibleLogoutDialog = it)
        }
    }

    fun currentUser() {
        _uIState.update { state ->
            state.copy(user = authRepository.getCurrentUser())
        }
    }

    fun signOut(navController: NavController) {
        authRepository.signOut()
        _uIState.update { state ->
            state.copy(visibleLogoutDialog = false)
        }
        navController.navigate(Screens.LOGIN_SCREEN) {
            popUpTo(navController.graph.findStartDestination().id){
                inclusive = true
            }
            this.launchSingleTop = true
        }
    }

    data class UIState(
        var visibleLogoutDialog: Boolean = false,
        var user: FirebaseUser? = null
    )
}