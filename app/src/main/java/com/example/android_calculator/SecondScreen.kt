package com.example.android_calculator



import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.navigation.NavHostController
import com.example.android_calculator.ui.theme.brightPink
import com.example.android_calculator.ui.theme.darkPink
import com.example.android_calculator.ui.theme.darkerPink
import com.example.android_calculator.ui.theme.lightPink
import kotlinx.coroutines.flow.StateFlow


@Composable
fun SecondScreen(
    stateFlow: StateFlow<CalculatorState>,
    buttonSpacing: Dp = 8.dp,
    modifier: Modifier = Modifier,
    onAction: (CalculatorAction) -> Unit,
    viewModel: CalculatorViewModel,
    navController: NavHostController
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
                text = state.displayed,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 60.dp),
                fontWeight = FontWeight.Light,
                fontSize = 60.sp,
                color = Color.White,
                maxLines = 1
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ){
                CalculatorButton(   symbol = state.deg,
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = { onAction(CalculatorAction.ChangeDeg)}
                )
                CalculatorButton(   symbol = "lg",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.String("log("))}
                )
                CalculatorButton(   symbol = "ln",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.String("ln("))}
                )
                CalculatorButton(   symbol = "<-",
                    modifier = Modifier
                        .background(lightPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.movePosition(-1))}
                )
                CalculatorButton(   symbol = "->",
                    modifier = Modifier
                        .background(lightPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.movePosition(+1))}
                )


            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ){
                CalculatorButton(   symbol = "cos",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.String("cos("))}
                )
                CalculatorButton(   symbol = "sin",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.String("sin("))}
                )
                CalculatorButton(   symbol = "tan",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.String("tan("))}
                )
                CalculatorButton(   symbol = "(",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Character('('))}
                )
                CalculatorButton(   symbol = ")",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Character(')'))}
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ){
                CalculatorButton(   symbol = "π",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Character('π'))}
                )

                CalculatorButton(   symbol = "AC",
                    modifier = Modifier
                        .background(lightPink)
                        .aspectRatio(2f)
                        .weight(2f),
                    onclick = {onAction(CalculatorAction.Clear)}
                )
                CalculatorButton(   symbol = "Del",
                    modifier = Modifier
                        .background(lightPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Delete)}
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

                CalculatorButton(   symbol = "%",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Character('%'))}
                )
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
                CalculatorButton(   symbol = "^",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Character('^'))}
                )
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
                CalculatorButton(   symbol = "!",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Character('!'))}
                )
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
                CalculatorButton(   symbol = "+",
                    modifier = Modifier
                        .background(brightPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Character('+'))}
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ){
                CalculatorButton(   symbol = "√",
                    modifier = Modifier
                        .background(darkPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.String("√("))}
                )
                CalculatorButton(   symbol = "⇋",
                    modifier = Modifier
                        .background(lightPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = { navController.navigate("first") }
                )
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
                        .background(brightPink)
                        .aspectRatio(1f)
                        .weight(1f),
                    onclick = {onAction(CalculatorAction.Calculate)}
                )
            }


        }
    }

}