package com.example.test_overcome.utils.ui.screen

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.test_overcome.ui.navigation.BottomNavigationDestination

@Composable
fun CustomBottomBar(navController: NavController, destinations: List<BottomNavigationDestination>) {
    val currentRoute = currentRoute(navController = navController)

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
        tonalElevation = 0.dp,
        containerColor = MaterialTheme.colorScheme.tertiary
    ) {
        destinations.forEach { destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = destination.icon),
                        contentDescription = "",
                        //tint = MaterialTheme.colorScheme.secondary
                    )
                },
                label = {

                    Text(text = stringResource(id = destination.title))

                },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = MaterialTheme.colorScheme.onTertiary,
                    selectedIconColor = MaterialTheme.colorScheme.onTertiary,
                    unselectedIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.tertiary
                ),
                interactionSource = MutableInteractionSource()

            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}