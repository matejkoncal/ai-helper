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
                var shouldContinue = false

                val dialog = AlertDialog.Builder(context)
                    .setTitle("AI")
                    .setMessage("Write a prompt for modifying your text:")
                    .setView(input)
                    .setOnDismissListener {
                        if (shouldContinue) {
                            val userInput = input.text.toString()
                            continuation.resume(userInput)
                        } else {
                            continuation.resume("");
                        }
                    }
                    .setNegativeButton("Close") { dialog, _ ->
                        dialog.cancel()
                    }.setPositiveButton("OK") { dialog, _ ->
                        shouldContinue = true
                        dialog.cancel()
                    }
                    .create()

                dialog.window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)

                dialog.show()
            }
        }
    }
}