package com.example.aihelper

import kotlinx.serialization.Serializable

@Serializable
data class ListItemSource(
    val prompt: String,
    val label: String
)