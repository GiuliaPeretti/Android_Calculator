package com.example.android_calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android_calculator.ui.theme.Android_calculatorTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
            /*
            Android_calculatorTheme {
                val viewModel = CalculatorViewModel()
                val state = viewModel.state
                val buttonSpacing = 8.dp
                Calculator(
                    stateFlow = state,
                    onAction = viewModel::onAction,
                    buttonSpacing = buttonSpacing,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .padding(16.dp),
                    viewModel = viewModel
                )
            }

             */
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val viewModel = CalculatorViewModel()
    val state = viewModel.state
    val buttonSpacing = 8.dp
    NavHost(navController = navController, startDestination = "first") {
        composable("first") { Calculator(
            stateFlow = state,
            onAction = viewModel::onAction,
            buttonSpacing = buttonSpacing,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(16.dp),
            viewModel = viewModel,
            navController = navController
        ) }
        composable("second") { SecondScreen(
            stateFlow = state,
            onAction = viewModel::onAction,
            buttonSpacing = buttonSpacing,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(16.dp),
            viewModel = viewModel,
            navController = navController) }
    }
}



