package com.example.test_overcome.utils.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun CustomDropDownMenu(
    modifier: Modifier = Modifier,
    value: String = "",
    enabled: Boolean = true,
    trailingIcon: Int? = null,
    expanded: Boolean = false,
    error: Boolean = false,
    hint: Int,
    label: Int,
    onExpanded: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            onExpanded(false)
        },
        modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() }),
        content = content

    )

    OutlinedTextField(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                textFieldSize = coordinates.size.toSize()
            },
        value = value,
        label = { Text(text = stringResource(id = label))},
        maxLines = 1,
        enabled = enabled,
        onValueChange = {
            onValueChange(it)
        },
        isError = error,
        trailingIcon = {
            if (trailingIcon != null) {
                IconButton(onClick = {
                    onExpanded(!expanded)
                }) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = trailingIcon),
                        contentDescription = null,
                    )
                }
            }
        },
        shape = RoundedCornerShape(20),
        readOnly = true,
        placeholder = {
            Text(
                text = stringResource(id = hint),
            )
        }

    )




}