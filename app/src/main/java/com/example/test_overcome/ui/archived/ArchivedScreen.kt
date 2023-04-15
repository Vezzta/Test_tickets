package com.example.test_overcome.ui.archived

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.test_overcome.R
import com.example.test_overcome.model.Ticket
import com.example.test_overcome.ui.home.HomeViewModel
import com.example.test_overcome.ui.home.add.dialog.AddTicketDialogForm
import com.example.test_overcome.ui.navigation.Screens
import com.example.test_overcome.utils.constants.Status
import com.example.test_overcome.utils.ui.Screen
import com.example.test_overcome.utils.ui.TicketItem

@Composable
fun ArchivedScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    LaunchedEffect(viewModel) {
        viewModel.getTickets()
    }

    var tickets by remember { mutableStateOf<List<Ticket?>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val uIState by viewModel.uIState.collectAsState()
    tickets = uIState.tickets
    isLoading = uIState.isLoading

    Screen(
        navController = navController,
        title = R.string.archived_tickets_title,
    ) {
        HomeContent(tickets.filter { it?.status == Status.ARCHIVED }, navController)
    }

}

@Composable
fun HomeContent(tickets: List<Ticket?>, navController: NavController) {

    var showDialog by remember { mutableStateOf(false) }

    AddTicketDialogForm(visibility = showDialog) {
        showDialog = false
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