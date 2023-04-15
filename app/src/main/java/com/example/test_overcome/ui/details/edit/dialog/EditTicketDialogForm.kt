package com.example.test_overcome.ui.details.edit.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.test_overcome.R
import com.example.test_overcome.utils.constants.Severity
import com.example.test_overcome.utils.constants.Teams
import com.example.test_overcome.utils.constants.Type
import com.example.test_overcome.utils.ui.CustomDropDownMenu

@Composable
fun EditTicketDialogForm(visibility: Boolean, id: Int, viewModel: EditTicketDialogFormViewModel = hiltViewModel(), onDissmiss: () -> Unit) {
    LaunchedEffect(viewModel){
        viewModel.getTicketById(id)
    }

    val verticalScroll = rememberScrollState()

    var title by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    val teamList = listOf(
        Teams.SUPPORT, Teams.DEVELOP, Teams.CUSTOMER_SERVICE
    )
    var teamSelected by remember { mutableStateOf("") }
    var teamExpanded by remember { mutableStateOf(false) }

    val typeList = listOf(
        Type.BUG, Type.FEATURE
    )
    var typeSelected by remember { mutableStateOf("") }
    var typeExpanded by remember { mutableStateOf(false) }

    val severityList = listOf(
        Severity.LOW, Severity.MEDIUM, Severity.HIGHT
    )
    var severitySelected by remember { mutableStateOf("") }
    var severityExpanded by remember { mutableStateOf(false) }

    var version by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val uIState by viewModel.uIState.collectAsState()
    title = uIState.title
    name = uIState.name
    teamSelected = uIState.teamSelected
    typeSelected = uIState.typeSelected
    severitySelected = uIState.severitySelected
    version = uIState.version
    description = uIState.description


    if (visibility) {
        Dialog(onDismissRequest = { onDissmiss() }) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.home_dialog_title),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 20.dp)
                            .verticalScroll(verticalScroll)
                    ) {

                        AddDialogTitle(title) { viewModel.setTitle(it) }
                        AddDialogName(name) { viewModel.setName(it) }
                        AddDialogTeam(
                            teamSelected = teamSelected,
                            teamList = teamList,
                            teamExpanded = teamExpanded,
                            onExpanded = { teamExpanded = it },
                            onClick = {
                                viewModel.setTeam(it)
                                teamExpanded = false
                            }
                        )
                        AddDialogType(
                            typeSelected = typeSelected,
                            typeList = typeList,
                            typeExpanded = typeExpanded,
                            onExpanded = { typeExpanded = it },
                            onClick = {
                                viewModel.setType(it)
                                typeExpanded = false
                            }
                        )
                        AddDialogSeveruty(
                            severitySelected = severitySelected,
                            severityList = severityList,
                            severityExpanded = severityExpanded,
                            onExpanded = { severityExpanded = it },
                            onClick = {
                                viewModel.setSeverity(it)
                                severityExpanded = false
                            }
                        )
                        AddDialogVersion(version = version, onValueChange = { viewModel.setVersion(it) })
                        AddDialogDescription(
                            description = description,
                            onValueChange = { viewModel.setDescription(it) })

                        //Aquí se agregan multimedias

                        //---------

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(onClick = {
                                onDissmiss()
                            }) {
                                Text(text = stringResource(id = R.string.home_dialog_button_cancel_label))
                            }
                            Button(onClick = {
                                viewModel.updateTicket(id){
                                    onDissmiss()
                                }
                            }) {
                                Text(text = stringResource(id = R.string.edit_dialog_button_update_label))
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun AddDialogTitle(title: String, onValueChange: (String) -> Unit) {

    OutlinedTextField(
        value = title,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                text = stringResource(id = R.string.home_dialog_title_label),
            )
        },
        placeholder = { Text(text = stringResource(id = R.string.home_dialog_title_label)) },
    )

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun AddDialogName(name: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = name,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                text = stringResource(id = R.string.home_dialog_name_label),
            )
        },
        placeholder = { Text(text = stringResource(id = R.string.home_dialog_name_label)) },
    )

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun AddDialogTeam(
    teamSelected: String,
    teamList: List<String>,
    teamExpanded: Boolean,
    onExpanded: (Boolean) -> Unit,
    onClick: (String) -> Unit
) {
    CustomDropDownMenu(
        value = teamSelected,
        trailingIcon = R.drawable.ic_down,
        expanded = teamExpanded,
        onExpanded = { onExpanded(it) },
        hint = R.string.home_dialog_team_label,
        label = R.string.home_dialog_team_label
    ) {
        teamList.forEach { item ->
            DropdownMenuItem(text = { Text(text = item) }, onClick = {
                onClick(item)
            })
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun AddDialogType(
    typeSelected: String,
    typeList: List<String>,
    typeExpanded: Boolean,
    onExpanded: (Boolean) -> Unit,
    onClick: (String) -> Unit
) {
    CustomDropDownMenu(
        value = typeSelected,
        trailingIcon = R.drawable.ic_down,
        expanded = typeExpanded,
        onExpanded = { onExpanded(it) },
        hint = R.string.home_dialog_type_label,
        label = R.string.home_dialog_type_label,
    ) {
        typeList.forEach { item ->
            DropdownMenuItem(text = { Text(text = item) }, onClick = {
                onClick(item)
            })
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun AddDialogSeveruty(
    severitySelected: String,
    severityList: List<String>,
    severityExpanded: Boolean,
    onExpanded: (Boolean) -> Unit,
    onClick: (String) -> Unit
) {
    CustomDropDownMenu(
        value = severitySelected,
        trailingIcon = R.drawable.ic_down,
        expanded = severityExpanded,
        onExpanded = { onExpanded(it) },
        hint = R.string.home_dialog_severity_label,
        label = R.string.home_dialog_severity_label,
    ) {
        severityList.forEach { item ->
            DropdownMenuItem(text = { Text(text = item) }, onClick = {
                onClick(item)
            })
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun AddDialogVersion(version: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = version,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                text = stringResource(id = R.string.home_dialog_version_label),
            )
        },
        placeholder = { Text(text = stringResource(id = R.string.home_dialog_version_label)) },
    )

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun AddDialogDescription(description: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = description,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                text = stringResource(id = R.string.home_dialog_description_label),
            )
        },
        placeholder = { Text(text = stringResource(id = R.string.home_dialog_description_label)) },
        minLines = 5,
        maxLines = 20,
    )

    Spacer(modifier = Modifier.height(20.dp))
}

