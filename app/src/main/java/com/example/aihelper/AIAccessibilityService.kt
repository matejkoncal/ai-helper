package com.example.aihelper

import android.accessibilityservice.AccessibilityButtonController
import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AIAccessibilityService : AccessibilityService() {
    private var node: AccessibilityNodeInfo? = null

    override fun onServiceConnected() {
        requestOverlayPermission(this)

        val accessibilityButtonCallback =
            object : AccessibilityButtonController.AccessibilityButtonCallback() {
                override fun onClicked(controller: AccessibilityButtonController) {
                    GlobalScope.launch {
                        node?.also {
                            val promptDialog = PromptDialog(baseContext)
                            val prompt = promptDialog.askForPrompt()
                            updateTextNode(it, prompt)
                        }
                    }
                }
            }

        accessibilityButtonController.registerAccessibilityButtonCallback(
            accessibilityButtonCallback
        )
    }

    override fun onInterrupt() {
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (rootInActiveWindow.packageName != "com.example.aihelper") {
            node = findFocusedChildNode(rootInActiveWindow)
        }
    }
}