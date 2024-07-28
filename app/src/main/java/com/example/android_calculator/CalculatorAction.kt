package com.example.android_calculator

sealed class CalculatorAction{
    data class Character(val c: Char): CalculatorAction()
    data class String(val s: kotlin.String): CalculatorAction()
    data class movePosition(val p: Int): CalculatorAction()
    object Clear: CalculatorAction()
    object Delete: CalculatorAction()
    object Decimal: CalculatorAction()
    object Calculate: CalculatorAction()
    object ChangeDeg: CalculatorAction()

}
