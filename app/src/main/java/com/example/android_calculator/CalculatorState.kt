package com.example.android_calculator

import javax.xml.xpath.XPathExpression

data class CalculatorState(
    var expression: String ="",
    var displayed: String ="",
    var pos: Int = 0,
    var deg: String = "deg"
)