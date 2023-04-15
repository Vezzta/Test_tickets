package com.example.test_overcome.utils.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    text: String,
    label: Int,
    hint: Int,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    error: Int = 0,
    onValueChange: (String) -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            label = { Text(text = stringResource(id = label)) },
            placeholder = {
                Text(
                    text = stringResource(id = hint),
                    color = MaterialTheme.colorScheme.outline
                )
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation
        )

        if (error != 0){
            Text(
                text = stringResource(id = error),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }

    }
}