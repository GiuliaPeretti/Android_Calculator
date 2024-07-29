package com.example.android_calculator


data class CalculatorState(
    var expression: String ="",
    var displayed: String ="",
    var pos: Int = 0,
    var deg: String = "deg"
)