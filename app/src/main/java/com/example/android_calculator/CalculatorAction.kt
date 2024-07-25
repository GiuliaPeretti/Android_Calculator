package com.example.android_calculator

sealed class CalculatorAction{
    data class Character(val c: Char): CalculatorAction()
    object Clear: CalculatorAction()
    object Delete: CalculatorAction()
    object Decimal: CalculatorAction()
    object Calculate: CalculatorAction()
}
