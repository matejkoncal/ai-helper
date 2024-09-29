package com.example.aihelper

import android.app.AlertDialog
import android.content.Context
import android.view.WindowManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class PromptDialog(private val context: Context) {

    suspend fun askForPrompt(): String {
        return withContext(Dispatchers.Main) {
            suspendCancellableCoroutine { continuation ->
                var shouldContinue = false
                val items = loadPrompts(context)
                var index = 0
                val strings = items.map { it.label }.toTypedArray();

                val dialog = AlertDialog.Builder(context).setItems(strings) { dialog, i ->
                        index = i
                        shouldContinue = true
                        dialog.cancel()
                    }.setOnDismissListener {
                        if (shouldContinue) {
                            continuation.resume(items[index].prompt)
                        } else {
                            continuation.resume("");
                        }
                    }.setNegativeButton("Close") { dialog, _ ->
                        dialog.cancel()
                    }.create()

                dialog.window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)

                dialog.show()
            }
        }
    }
}