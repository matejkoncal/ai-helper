package com.example.aihelper

import android.app.AlertDialog
import android.content.Context
import android.view.WindowManager
import android.widget.EditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class PromptDialog(private val context: Context) {

    suspend fun askForPrompt(): String {
        return withContext(Dispatchers.Main) {
            suspendCancellableCoroutine { continuation ->
                val input = EditText(context)

                val dialog = AlertDialog.Builder(context)
                    .setTitle("AI")
                    .setMessage("Write a prompt for modifying your text:")
                    .setView(input)
                    .setOnDismissListener {
                        val userInput = input.text.toString()
                        continuation.resume(userInput)
                    }
                    .setNegativeButton("OK") { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()

                dialog.window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)

                dialog.show()
            }
        }
    }
}