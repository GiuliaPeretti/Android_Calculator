package com.example.android_calculator

sealed class CalculatorOperation(val symbol: String) {
    object  Add:  CalculatorOperation("+")
    object  Subtraction:  CalculatorOperation("-")
    object  Multiply:  CalculatorOperation("*")
    object  Divide:  CalculatorOperation("/")
}
