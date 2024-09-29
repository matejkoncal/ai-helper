package com.example.aihelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.aihelper.ui.AddNewPromptDialog
import com.example.aihelper.ui.PromptListItem
import com.example.aihelper.ui.theme.AIHelperTheme
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIHelperTheme {
                var showDialog by remember { mutableStateOf(false) }
                val itemList = remember { mutableStateListOf<ListItemSource>() }
                var isLoaded by remember { mutableStateOf(false) }

                LaunchedEffect(itemList.toList()) {
                    if (isLoaded) {
                        val serialized = Json.encodeToString(itemList.toList())
                        val file = File(this@MainActivity.filesDir, "prompts.json")
                        FileOutputStream(file).use { stream ->
                            stream.write(serialized.toByteArray())
                        }
                    }
                }

                LaunchedEffect(Unit) {
                    loadPrompts(this@MainActivity).let { itemList.addAll(it) }
                    isLoaded = true
                }

                Scaffold(floatingActionButton = {
                    if (itemList.count() < 6) FloatingActionButton(
                        onClick = {
                            showDialog = true
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White,
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Add")
                    }
                }) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        itemList.forEach { item ->
                            PromptListItem(itemSource = item, onDeleteClick = {
                                itemList.remove(item)
                            })
                        }
                    }
                    if (showDialog) AddNewPromptDialog(onDismiss = {
                        showDialog = false
                    }) { label, prompt ->
                        itemList.add(ListItemSource(prompt, label))
                        showDialog = false
                    }
                }
            }
        }
    }
}
