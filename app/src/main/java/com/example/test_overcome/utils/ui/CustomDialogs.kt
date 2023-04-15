package com.example.test_overcome.utils.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.test_overcome.R

@Composable
fun SuccessDialog(title: Int = 0, text: Int = 0, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.outlineVariant)
                    .clickable {
                        onDismiss()
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                if (title != 0) {
                    Text(
                        text = stringResource(id = title),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
                if (text != 0) {
                    Text(
                        text = stringResource(id = text),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun LogoutDialog(visible: Boolean, onDismiss: () -> Unit, onAccept: () -> Unit) {
    if (visible) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Card(modifier = Modifier) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.outlineVariant)
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = R.string.logout_dialog_title),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier.padding(bottom = 20.dp),
                        text = stringResource(R.string.logout_dialog_text),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center

                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp),
                        onClick = { onAccept() }) {
                        Text(text = stringResource(id = R.string.logout_dialog_accept_button))
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp),
                        onClick = { onDismiss() }) {
                        Text(text = stringResource(id = R.string.logout_dialog_cancel_button))
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                }
            }
        }
    }
}