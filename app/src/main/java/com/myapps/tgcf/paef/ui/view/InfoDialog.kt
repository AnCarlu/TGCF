package com.myapps.tgcf.paef.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapps.tgcf.R

@Composable
fun InfoDialog() {
    var status by remember { mutableStateOf(true) }
    if (status) {

        AlertDialog(
            onDismissRequest = { status = false },
            confirmButton = {
                Button(onClick = { status = false }) {
                    Text(text = stringResource(R.string.accept), fontSize = 12.sp)
                }
            },
            title = {
                Text(
                    text = stringResource(R.string.infoTitleOpen),
                    modifier = Modifier.padding(2.dp),
                    fontSize = 16.sp
                )
            },
            text = { Text(text = stringResource(R.string.infoUseApp)) }
        )
    }
}