package com.example.test_overcome.utils.ui.screen.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.test_overcome.R
import com.example.test_overcome.ui.navigation.Screens
import com.example.test_overcome.utils.ui.LogoutDialog
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope

@Composable
fun CustomDrawer(
    navController: NavController,
    drawerState: DrawerState,
    toolbarScope: CoroutineScope,
    viewModel: CustomDrawerViewModel = hiltViewModel()
) {

    LaunchedEffect(viewModel) {
        viewModel.currentUser()
    }

    var visibleDialog by remember { mutableStateOf(false) }
    var user by remember { mutableStateOf<FirebaseUser?>(null) }

    val uIState by viewModel.uIState.collectAsState()
    visibleDialog = uIState.visibleLogoutDialog
    user = uIState.user

    if (user != null){
        CustomDrawerContent(
            navController = navController,
            drawerState = drawerState,
            toolbarScope = toolbarScope,
            user = user!!,
            viewModel = viewModel
        )
    }

    LogoutDialog(visible = visibleDialog, onDismiss = { viewModel.setVisibleDialog(false) }) {
        viewModel.signOut(navController)
    }
}

@Composable
fun CustomDrawerContent(
    navController: NavController,
    drawerState: DrawerState,
    toolbarScope: CoroutineScope,
    user: FirebaseUser,
    viewModel: CustomDrawerViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {

        UserInfo(user)

        val destinationList = listOf(
            Destination(
                route = Screens.HOME_SCREEN,
                icon = R.drawable.ic_add_ticket,
                text = R.string.home_tickets_title
            ),
            Destination(
                route = Screens.PROCESS_SCREEN,
                icon = R.drawable.ic_tickets_in_progress,
                text = R.string.progress_tickets_title
            ),
            Destination(
                route = Screens.FINISHED_SCREEN,
                icon = R.drawable.ic_check,
                text = R.string.finished_tickets_title
            ),
            Destination(
                route = Screens.ARCHIVED_SCREEN,
                icon = R.drawable.ic_folder,
                text = R.string.archived_tickets_title
            ),
        )

        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 40.dp, vertical = 10.dp),
            ) {
                item { Spacer(modifier = Modifier.height(40.dp)) }
                items(destinationList) {
                    DestinationItem(
                        navController = navController,
                        drawerState = drawerState,
                        toolbarScope = toolbarScope,
                        route = it.route,
                        icon = it.icon,
                        text = it.text
                    )
                    //Spacer(modifier = Modifier.height(5.dp))
                }
                item {
                    Spacer(modifier = Modifier.height(160.dp))
                    TextButton(onClick = {viewModel.setVisibleDialog(true) }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_logout),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = stringResource(id = R.string.drawer_menu_logout_title),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(40.dp))
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun UserInfo(user: FirebaseUser) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = user.displayName ?: "",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(50.dp))
    }
}