package com.example.test_overcome.ui.pivot

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.test_overcome.ui.navigation.Screens

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PivotScreen(navController: NavController, viewModel: PivotViewModel = hiltViewModel()) {

    if (viewModel.currentUser() != null) {
        navController.navigate(Screens.HOME_SCREEN) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    } else {
        navController.navigate(Screens.LOGIN_SCREEN) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }
}