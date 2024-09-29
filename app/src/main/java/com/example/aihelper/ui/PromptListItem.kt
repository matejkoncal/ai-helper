package com.example.aihelper.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aihelper.ListItemSource


@Composable
fun PromptListItem(
    itemSource: ListItemSource,
    onClick: () -> Unit = {},
    onDeleteClick: () -> Unit
) {
    ListItem(
        modifier = Modifier
            .padding(5.dp)
            .clickable(onClick = onClick),
        trailingContent = {
            IconButton(onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        },
        shadowElevation = 5.dp,
        headlineContent = { Text(text = itemSource.label) },
        supportingContent = { Text(text = itemSource.prompt) })
}