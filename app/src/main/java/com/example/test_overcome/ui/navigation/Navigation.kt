package com.example.test_overcome.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.test_overcome.ui.archived.ArchivedScreen
import com.example.test_overcome.ui.details.DetailsScreen
import com.example.test_overcome.ui.finished.FinishedScreen
import com.example.test_overcome.ui.home.HomeScreen
import com.example.test_overcome.ui.login.LoginScreen
import com.example.test_overcome.ui.pivot.PivotScreen
import com.example.test_overcome.ui.process.ProcessScreen

@Composable
fun Navigation(
    navController: NavController
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screens.PIVOT_SCREEN
    ) {
        composable(Screens.HOME_SCREEN) {
            HomeScreen(navController = navController)
        }
        composable(Screens.PROCESS_SCREEN) {
            ProcessScreen(navController = navController)
        }
        composable(Screens.FINISHED_SCREEN) {
            FinishedScreen(navController = navController)
        }
        composable(
            route = Screens.DETAILS_SCREEN + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("id") ?: 0
            DetailsScreen(navController = navController, id = id)
        }
        composable(Screens.LOGIN_SCREEN){
            LoginScreen(navController = navController)
        }
        composable(Screens.PIVOT_SCREEN){
            PivotScreen(navController = navController)
        }
        composable(Screens.ARCHIVED_SCREEN){
            ArchivedScreen(navController = navController)
        }
    }
}