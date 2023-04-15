package com.example.test_overcome.ui.login

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.test_overcome.R
import com.example.test_overcome.ui.navigation.Screens
import com.example.test_overcome.utils.ui.SuccessDialog
import com.example.test_overcome.utils.ui.screen.CustomTextField

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val context = LocalContext.current

    LoginContent(navController, viewModel, context)

}

@Composable
fun LoginContent(navController: NavController, viewModel: LoginViewModel, context: Context) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState)
    ) {
        LoginFrontImage()
        LoginForm(navController, viewModel, context)

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )

        LoginRegistry(viewModel, navController, context)

    }

}

@Composable
fun LoginFrontImage() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            painter = painterResource(id = R.drawable.img_portada),
            contentDescription = null,
            alignment = Alignment.Center
        )
    }
}

@Composable
fun LoginForm(navController: NavController, viewModel: LoginViewModel, context: Context) {
    val focusManager = LocalFocusManager.current

    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(0) }
    var passError by remember { mutableStateOf(0) }

    val uiState by viewModel.uIState.collectAsState()
    email = uiState.email
    pass = uiState.pass
    emailError = uiState.emailError
    passError = uiState.passError

    var successDialog by remember { mutableStateOf(false) }

    if (successDialog) {
        SuccessDialog(title = R.string.login_success_dialog_title) {
            navController.navigate(Screens.HOME_SCREEN) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
            }
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    CustomTextField(
        text = email, R.string.login_email_label, R.string.login_email_hint, KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ), KeyboardActions(onDone = {
            focusManager.moveFocus(FocusDirection.Down)
        }),
        error = emailError
    ) {
        viewModel.setEmail(it)
    }

    CustomTextField(
        text = pass,
        label = R.string.login_pass_label,
        hint = R.string.login_pass_hint,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            viewModel.signIn(email, pass, context) {
                successDialog = true
            }
        }),
        visualTransformation = PasswordVisualTransformation(),
        error = passError
    ) {
        viewModel.setPass(it)
    }

    Spacer(modifier = Modifier.height(40.dp))

    Button(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 40.dp),
        onClick = {
            viewModel.signIn(email, pass, context) {
                successDialog = true
            }
        }) {
        Text(text = stringResource(id = R.string.login_button_text))
    }

    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(id = R.string.login_remember_pass),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {

            },
        textAlign = TextAlign.End
    )

    Spacer(modifier = Modifier.height(10.dp))

}

@Composable
fun LoginRegistry(viewModel: LoginViewModel, navController: NavController, context: Context) {
    var showDialog by remember { mutableStateOf(false) }
    var successDialog by remember { mutableStateOf(false) }

    if (successDialog) {
        SuccessDialog(
            title = R.string.registry_success_dialog_title,
            text = R.string.registry_success_dialog_text
        ) {
            navController.navigate(Screens.HOME_SCREEN) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
            }
        }
    }

    if (showDialog) {
        RegistryDialog(viewModel, context, onDismiss = {
            showDialog = false
        }) {
            viewModel.setRegistryEmail("")
            viewModel.setRegistryPass("")
            viewModel.setConfirmPass("")
            successDialog = true
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        onClick = { showDialog = true }) {
        Text(text = stringResource(id = R.string.login_registry_button))
    }

    Spacer(modifier = Modifier.height(20.dp))


    Spacer(modifier = Modifier.height(40.dp))
}


@Composable
fun RegistryDialog(
    viewModel: LoginViewModel,
    context: Context,
    onDismiss: () -> Unit,
    onRegistry: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    var name by remember { mutableStateOf("")}
    var nameError by remember { mutableStateOf(0)}
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(0) }
    var pass by remember { mutableStateOf("") }
    var passError by remember { mutableStateOf(0) }
    var confirmPass by remember { mutableStateOf("") }
    var confirmPassError by remember { mutableStateOf(0) }

    val uiState by viewModel.uIState.collectAsState()
    name = uiState.name
    nameError = uiState.nameError
    email = uiState.registryEmail
    emailError = uiState.registryEmailError
    pass = uiState.registryPass
    passError = uiState.registryPassError
    confirmPass = uiState.confirmPass
    confirmPassError = uiState.confirmPassError


    Dialog(onDismissRequest = { onDismiss() }) {

        Card(
            modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 20.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)

            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Regístrate",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(40.dp))

                CustomTextField(
                    text = name,
                    label = R.string.login_name_label,
                    hint = R.string.login_name_hint,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    error = nameError,
                    onValueChange = {
                        viewModel.setName(it)
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                CustomTextField(
                    text = email,
                    label = R.string.login_email_label,
                    hint = R.string.login_email_hint,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    error = emailError,
                    onValueChange = {
                        viewModel.setRegistryEmail(it)
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                CustomTextField(
                    text = pass,
                    label = R.string.login_pass_label,
                    hint = R.string.login_pass_hint,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    visualTransformation = PasswordVisualTransformation(),
                    error = passError
                ) {
                    viewModel.setRegistryPass(it)
                }

                Spacer(modifier = Modifier.height(20.dp))

                CustomTextField(
                    text = confirmPass,
                    label = R.string.login_pass_confirm_label,
                    hint = R.string.login_pass_confirm_hint,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        //On done
                    }),
                    visualTransformation = PasswordVisualTransformation(),
                    error = confirmPassError
                ) {
                    viewModel.setConfirmPass(it)
                }

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    onClick = {
                        viewModel.createUser(email, pass, context, name) {
                            onRegistry()
                        }
                    }) {
                    Text(text = stringResource(id = R.string.login_registry_button))
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}