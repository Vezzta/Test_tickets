package com.example.test_overcome.utils.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.test_overcome.App
import com.example.test_overcome.R
import com.example.test_overcome.utils.ui.screen.CustomBottomBar
import com.example.test_overcome.utils.ui.screen.drawer.CustomDrawer

@Composable
fun Screen(
    title: Int = R.string.app_name,
    navController: NavController,
    back: Boolean = false,
    showBottomBar: Boolean = true,
    content: @Composable () -> Unit = {},
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val toolbarScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(300.dp)) {
                CustomDrawer(
                    navController = navController,
                    drawerState = drawerState,
                    toolbarScope = toolbarScope,
                )
            }
        }) {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                CustomTopBar(
                    navController = navController,
                    drawerState = drawerState,
                    title = title,
                    back = back
                )
            }, bottomBar = {
                if (showBottomBar) {
                    CustomBottomBar(
                        navController = navController,
                        destinations = App.destinations
                    )
                }
            }) {
            Column(
                modifier = Modifier
                    .padding(top = 75.dp)
                    .padding(bottom = if (showBottomBar) 80.dp else 0.dp)
                    .fillMaxSize(),
            ) {
                content()
            }
        }
    }
}