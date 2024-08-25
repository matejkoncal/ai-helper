package com.example.aihelper

import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class FunctionResult(val text: String)

class AIService {
    suspend fun adjustText(text: String, prompt: String): String {
        val data = hashMapOf("text" to text, "prompt" to prompt)
        val serializedResult =  Firebase.functions.getHttpsCallable("adjustText").call(data).await().data as String
        return Json.decodeFromString<FunctionResult>(serializedResult).text
    }
}