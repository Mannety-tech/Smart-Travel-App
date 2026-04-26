package com.example.leedstrinity.app.chat


data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long
)


