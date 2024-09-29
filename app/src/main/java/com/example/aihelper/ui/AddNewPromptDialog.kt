package com.example.aihelper.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddNewPromptDialog(
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Create your prompt") },
        text = {
            Column {
                OutlinedTextField(
                    value = text1,
                    onValueChange = { text1 = it },
                    label = { Text("Label") }

                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = text2,
                    onValueChange = { text2 = it },
                    label = { Text("Prompt") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(text1, text2)
                    onDismiss()
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
