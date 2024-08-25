package com.example.aihelper

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.accessibility.AccessibilityNodeInfo

fun updateTextNode(node: AccessibilityNodeInfo, newText: String) {
    if(!node.isEditable){
        throw Error("Node is not editable.")
    }

    val bundle = Bundle().apply {
        putCharSequence(
            AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
            newText
        )
    }
    node.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
    node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle)
}

fun hasOverlayPermission(context: Context): Boolean {
    return Settings.canDrawOverlays(context)
}

fun requestOverlayPermission(context: Context) {
    if (!hasOverlayPermission(context)) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${context.packageName}")
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}

fun findFocusedChildNode(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
    val stack = mutableListOf(node)

    while (stack.isNotEmpty()) {
        val currentNode = stack.removeAt(stack.size - 1)

        if (currentNode.isFocused) {
            return currentNode
        }

        for (i in 0 until currentNode.childCount) {
            stack.add(currentNode.getChild(i))
        }
    }

    return null
}
