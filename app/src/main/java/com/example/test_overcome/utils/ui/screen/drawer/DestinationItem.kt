package com.example.test_overcome.utils.ui.screen.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DestinationItem(
    navController: NavController,
    drawerState: DrawerState,
    toolbarScope: CoroutineScope,
    route: String,
    icon: Int,
    text: Int
) {
    TextButton(
        onClick = {
            toolbarScope.launch {
                drawerState.close()
                navController.navigate(route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = true
                    }
                    this.launchSingleTop = true
                }
            }
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(27.dp),
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = stringResource(id = text),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
        }

    }
}