package com.example.android_calculator

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_calculator.ui.theme.brightPink
import com.example.android_calculator.ui.theme.darkPink
import com.example.android_calculator.ui.theme.lightPink
import kotlinx.coroutines.flow.StateFlow

@Composable
fun Calculator(
    stateFlow: StateFlow<CalculatorState>,
    buttonSpacing: Dp = 8.dp,
    modifier: Modifier = Modifier,
    onAction: (CalculatorAction) -> Unit,
    viewModel: CalculatorViewModel
) {
    val state by viewModel.state.collectAsState()
    Box(modifier = Modifier){
        Column (

            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            Text(
                text = state.expression,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                fontWeight = FontWeight.Light,
                fontSize = 60.sp,
                color = Color.White,
                maxLines = 2
            )
            Log.d("deb", "Calculator")
            Log.d("deb", state.expression)


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ){
            CalculatorButton(   symbol = "Del",
                modifier = Modifier
                    .background(lightPink)
                    .aspectRatio(1f)
                    .weight(1f),
                onclick = {onAction(CalculatorAction.Delete)}
            )
            CalculatorButton(   symbol = "(",
                modifier = Modifier
                    .background(lightPink)
                    .aspectRatio(1f)
                    .weight(1f),
                onclick = {onAction(CalculatorAction.Character('('))}
            )
            CalculatorButton(   symbol = ")",
                modifier = Modifier
                    .background(lightPink)
                    .aspectRatio(1f)
                    .weight(1f),
                onclick = {onAction(CalculatorAction.Character(')'))}
            )
            CalculatorButton(   symbol = "^",
                modifier = Modifier
                    .background(brightPink)
                    .aspectRatio(1f)
                    .weight(1f),
                onclick = {onAction(CalculatorAction.Character('^'))}
            )

        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ){
            CalculatorButton(   symbol = "7",
                modifier = Modifier
                    .background(darkPink)
                    .aspectRatio(1f)
                    .weight(1f),
                onclick = {onAction(CalculatorAction.Character('7'))}
            )
            CalculatorButton(   symbol = "8",
                modifier = Modifier
                    .background(darkPink)
                    .aspectRatio(1f)
                    .weight(1f),
                onclick = {onAction(CalculatorAction.Character('8'))}
            )
            CalculatorButton(   symbol = "9",
                modifier = Modifier
                    .background(darkPink)
                    .aspectRatio(1f)
                    .weight(1f),
                onclick = {onAction(CalculatorAction.Character('9'))}
            )
            CalculatorButton(   symbol = "/",
                modifier = Modifier
                    .background(brightPink)
                    .aspectRatio(1f)
                    .weight(1f),
                onclick = {onAction(CalculatorAction.Character('/'))}
            )


        }

        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ){
                CalculatorButton(   symbol = "4",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Character('4'))}
                )
                CalculatorButton(   symbol = "5",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Character('5'))}
                )
                CalculatorButton(   symbol = "6",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Character('6'))}
                )
                CalculatorButton(   symbol = "x",
                    modifier = Modifier
                        .background(brightPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Character('x'))}
                )


            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ){
                CalculatorButton(   symbol = "1",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Character('1'))}
                )
                CalculatorButton(   symbol = "2",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Character('2'))}
                )
                CalculatorButton(   symbol = "3",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Character('3'))}
                )
                CalculatorButton(   symbol = "-",
                    modifier = Modifier
                        .background(brightPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Character('-'))}
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ){
                CalculatorButton(   symbol = "0",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Character('0'))}
                )
                CalculatorButton(   symbol = ".",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Decimal)}
                )
                CalculatorButton(   symbol = "=",
                    modifier = Modifier
                        .background(lightPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Calculate)}
                )
                CalculatorButton(   symbol = "+",
                    modifier = Modifier
                        .background(brightPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Character('+'))}
                )
            }


        }
    }

}