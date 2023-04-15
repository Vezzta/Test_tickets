package com.example.test_overcome.utils.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.test_overcome.R
import kotlinx.coroutines.launch

@Composable
fun CustomTopBar(
    title: Int = R.string.app_name,
    navController: NavController,
    back: Boolean = false,
    drawerState: DrawerState,
) {
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .clip(
                shape = RoundedCornerShape(
                    bottomStartPercent = 50,
                    bottomEndPercent = 50
                )
            )
            .background(MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (back) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(start = 30.dp)
                    .clickable { navController.popBackStack() }
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(start = 30.dp)
                    .clickable {
                        scope.launch { drawerState.open() }
                    }
            )
        }

        Text(
            modifier = Modifier,
            text =  stringResource(id = title),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary

        )

        Spacer(modifier = Modifier.width(55.dp))
    }
}