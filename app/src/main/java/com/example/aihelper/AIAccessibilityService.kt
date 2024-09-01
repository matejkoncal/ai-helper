package com.example.aihelper

import android.accessibilityservice.AccessibilityButtonController
import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AIAccessibilityService : AccessibilityService() {
    private var node: AccessibilityNodeInfo? = null

    override fun onServiceConnected() {
        requestOverlayPermission(this)

        var ctx = this;
        val accessibilityButtonCallback =
            object : AccessibilityButtonController.AccessibilityButtonCallback() {
                override fun onClicked(controller: AccessibilityButtonController) {
                    val focusedNode = findFocusedChildNode(rootInActiveWindow)
                    if (focusedNode?.isEditable == true && focusedNode?.packageName != "com.example.aihelper")
                        GlobalScope.launch {
                            focusedNode?.also {
                                it.refresh()

                                val promptDialog = PromptDialog(baseContext)
                                val prompt = promptDialog.askForPrompt()

                                if (prompt.isNotBlank()) {
                                    val adjustedText =
                                        AIService().adjustText(it.text.toString(), prompt)
                                    updateTextNode(it, adjustedText)
                                }
                            }
                        }
                    else
                        Toast.makeText(ctx, "Click to editable input", Toast.LENGTH_LONG).show()
                }
            }

        accessibilityButtonController.registerAccessibilityButtonCallback(
            accessibilityButtonCallback
        )
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    }

    override fun onInterrupt() {
    }
}