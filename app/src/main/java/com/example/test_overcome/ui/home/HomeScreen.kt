package com.example.test_overcome.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.test_overcome.R
import com.example.test_overcome.model.Ticket
import com.example.test_overcome.ui.home.add.dialog.AddTicketDialogForm
import com.example.test_overcome.ui.navigation.Screens
import com.example.test_overcome.utils.constants.Status
import com.example.test_overcome.utils.ui.LoadingContent
import com.example.test_overcome.utils.ui.Screen
import com.example.test_overcome.utils.ui.TicketItem

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    LaunchedEffect(viewModel) {
        viewModel.getTickets()
    }

    var tickets by remember { mutableStateOf<List<Ticket?>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    val uIState by viewModel.uIState.collectAsState()
    tickets = uIState.tickets
    isLoading = uIState.isLoading


    Screen(navController = navController, title = R.string.home_tickets_title) {
        LoadingContent(isLoading = isLoading, modifier = Modifier.fillMaxSize()) {
            HomeContent(tickets.filter { it?.status == Status.NEW }, navController)
        }
    }
}

@Composable
fun HomeContent(tickets: List<Ticket?>, navController: NavController) {

    var showDialog by remember { mutableStateOf(false) }

    AddTicketDialogForm(visibility = showDialog) {
        showDialog = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_ticket),
                contentDescription = null,
                modifier = Modifier
                    .zIndex(1f)
                    .padding(bottom = 10.dp)
                    .padding(end = 10.dp)
                    .size(85.dp)
                    .clickable {
                        showDialog = true
                    },
                tint = MaterialTheme.colorScheme.primary
            )
        }

        if (tickets.isNotEmpty()) {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(tickets) {
                    TicketItem(
                        id = it?.id ?: 0,
                        title = it?.title ?: "",
                        severity = it?.severity ?: "",
                        type = it?.type ?: ""
                    ) {
                        navController.navigate(Screens.DETAILS_SCREEN + "/${it?.id}")
                    }
                }
            }
        }
    }
}



