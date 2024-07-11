package com.example.android_calculator

data class CalculatorState(
    var number1: String = "",
    var number2: String = "",
    var operation: CalculatorOperation? = null
)