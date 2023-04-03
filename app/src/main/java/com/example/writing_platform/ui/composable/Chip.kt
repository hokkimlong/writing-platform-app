package com.example.writing_platform.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.writing_platform.ui.theme.LightGray

@Composable
fun Chip(value:String) {
    OutlinedButton(
        onClick = { },
        border = BorderStroke(1.dp,  LightGray),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(backgroundColor =  LightGray),
        contentPadding = PaddingValues(7.dp,0.dp),

        modifier = Modifier
            .defaultMinSize(minWidth = ButtonDefaults.MinWidth, minHeight = 1.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, ) {
            Text(text = "#", fontWeight = FontWeight.Bold, color = MaterialTheme.colors.primary, fontSize =18.sp)
            Text(text = value, fontWeight = FontWeight.Normal)
        }
    }
}