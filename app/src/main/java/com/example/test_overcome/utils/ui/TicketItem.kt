package com.example.test_overcome.utils.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.test_overcome.utils.constants.Severity

@Composable
fun TicketItem(
    id: Int,
    title: String,
    severity: String,
    type: String,
    onClick: () -> Unit
) {

    val cardColors = mapOf(
        Severity.HIGHT to MaterialTheme.colorScheme.error,
        Severity.MEDIUM to MaterialTheme.colorScheme.tertiaryContainer,
        Severity.LOW to MaterialTheme.colorScheme.primaryContainer
    )

    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                onClick()
            }, elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = cardColors[severity] ?: MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = id.toString(),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Spacer(modifier = Modifier.height(40.dp))

            Text(text = type, style = MaterialTheme.typography.titleMedium)
            Text(text = severity, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
        }
    }
}