package com.example.android_calculator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.android_calculator.ui.theme.Android_calculatorTheme

@Composable
fun CalculatorButton(
    symbol: String,
    modifier: Modifier,
    onclick:() -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onclick() }
            .then(modifier)
    ){
        Text(
            text = symbol,
            fontSize = 36.sp,
            color = Android_calculatorTheme().onBackground
        )
    }
}
