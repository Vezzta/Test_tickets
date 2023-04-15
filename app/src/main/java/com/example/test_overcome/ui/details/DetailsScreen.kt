package com.example.test_overcome.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.test_overcome.R
import com.example.test_overcome.model.Ticket
import com.example.test_overcome.ui.details.edit.dialog.EditTicketDialogForm
import com.example.test_overcome.utils.constants.Severity
import com.example.test_overcome.utils.constants.Status
import com.example.test_overcome.utils.ui.Screen

@Composable
fun DetailsScreen(
    navController: NavController,
    id: Int,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.getTicketById(id)
    }

    var ticket by remember { mutableStateOf<Ticket?>(Ticket()) }
    var isLoading by remember { mutableStateOf(true) }

    val uIState by viewModel.uIState.collectAsState()
    ticket = uIState.ticket
    isLoading = uIState.isLoadin

    Screen(
        navController = navController,
        title = R.string.details_screen_title,
        back = true,
        showBottomBar = false
    ) {
        ticket?.let { TicketDetailsContent(it, viewModel, navController, id) }
    }
}

@Composable
fun TicketDetailsContent(
    ticket: Ticket,
    viewModel: DetailsViewModel,
    navController: NavController,
    id: Int,
) {
    val scrollState = rememberScrollState()
    val cardColors = mapOf(
        Severity.HIGHT to MaterialTheme.colorScheme.error,
        Severity.MEDIUM to MaterialTheme.colorScheme.tertiaryContainer,
        Severity.LOW to MaterialTheme.colorScheme.primaryContainer
    )

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColors[ticket.severity] ?: MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 30.dp)
                .verticalScroll(scrollState)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = ticket.id.toString(), style = MaterialTheme.typography.titleLarge)
                Text(text = ticket.date ?: "")
            }
            Spacer(modifier = Modifier.height(20.dp))

            if (ticket.status != Status.ARCHIVED) {
                EditTicket(id)
            }

            Text(
                text = ticket.title ?: "",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.details_screen_name_label),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "${ticket.responsible}")

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.details_screen_team_label),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "${ticket.team}")

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.details_screen_type_label),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "${ticket.type}")

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.details_screen_severity_label),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "${ticket.severity}")

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.details_screen_version_label),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "${ticket.version}")

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.details_screen_description_label),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "${ticket.description}")

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.details_screen_evidence_label),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()){
                ticket.files?.imageUrl.let {
                    LoadImages(images = ticket.files!!.imageUrl)
                }
            }

            DetailsButtons(ticket = ticket, navController, viewModel)
            Spacer(modifier = Modifier.height(20.dp))

            if (ticket.status != Status.ARCHIVED) {
                ArchivedTicket(navController, viewModel)
                Spacer(modifier = Modifier.height(20.dp))
            }

        }
    }

}

@Composable
fun DetailsButtons(ticket: Ticket, navController: NavController, viewModel: DetailsViewModel) {
    Spacer(modifier = Modifier.height(40.dp))
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        when (ticket.status) {
            Status.NEW -> {
                Button(onClick = {
                    navController.popBackStack()
                    viewModel.updateTicket(Status.IN_PROGRESS)
                }) {
                    Text(
                        text = stringResource(id = R.string.details_screen_button_new),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            Status.IN_PROGRESS -> {
                Button(onClick = {
                    navController.popBackStack()
                    viewModel.updateTicket(Status.FINISHED)
                }) {
                    Text(
                        text = stringResource(id = R.string.details_screen_button_process),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            Status.FINISHED -> {
                Spacer(modifier = Modifier.height(40.dp))
            }
            Status.ARCHIVED -> {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun EditTicket(id: Int) {
    var showDialog by remember { mutableStateOf(false) }

    EditTicketDialogForm(visibility = showDialog, id = id) {
        showDialog = false
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Image(
            painter = painterResource(id = R.drawable.ic_edit),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    showDialog = true
                },
        )
    }
}

@Composable
fun ArchivedTicket(navController: NavController, viewModel: DetailsViewModel) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Image(
            painter = painterResource(id = R.drawable.ic_folder),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    navController.popBackStack()
                    viewModel.updateTicket(Status.ARCHIVED)
                },
        )
    }
}

@Composable
fun LoadImages(images: List<String?>) {
    images.forEach {
        Image(
            modifier = Modifier
                .size(100.dp),
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = it)
                    .apply(block = fun ImageRequest.Builder.() {
                        error(R.drawable.no_image)
                            .memoryCachePolicy(
                                CachePolicy.DISABLED
                            )
                            .diskCachePolicy(CachePolicy.DISABLED)
                    }).build()
            ),
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(10.dp))
    }
}