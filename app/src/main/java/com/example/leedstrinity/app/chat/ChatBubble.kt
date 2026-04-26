package com.example.leedstrinity.app.chat


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ChatBubble(message: ChatMessage) {
    val bg = if (message.isUser) Color(0xFFDCF8C6) else Color(0xFFEFEFEF)
    val alignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .background(bg, shape = RoundedCornerShape(12.dp))
                .padding(12.dp)
                .widthIn(max = 260.dp)
        ) {
            Text(message.text)
        }
    }
}


