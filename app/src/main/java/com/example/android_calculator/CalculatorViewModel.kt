package com.example.android_calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.pow

class CalculatorViewModel: ViewModel() {
    private val _state = MutableStateFlow(CalculatorState())
    val state: StateFlow<CalculatorState> = _state.asStateFlow()

    fun onAction(action: CalculatorAction) {
        when(action){
            is CalculatorAction.Character -> enterChar(c = action.c)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Clear -> performClear()
            is CalculatorAction.Calculate -> performOperation()
            is CalculatorAction.Delete -> performeDelete()
        }
    }

    private fun performClear() {
        _state.value = CalculatorState()
    }

    private fun performeDelete() {
        _state.value = _state.value.copy(
            expression = _state.value.expression.dropLast(1)
        )
    }

    private fun performOperation() {
        while(!isDouble(_state.value.expression)) {
            if (_state.value.expression.contains("*", ignoreCase = true)
                || _state.value.expression.contains("/", ignoreCase = true)
                || _state.value.expression.contains("^", ignoreCase = true)
            ) {
                for (i in 1.._state.value.expression.length) {
                    if (_state.value.expression[i] == '*' || _state.value.expression[i] == '/' || _state.value.expression[i] == '^') {
                        val indexNumbers = getNumbers(i)
                        val n1: Double =
                            _state.value.expression.substring(indexNumbers[0], i).toDouble()
                        val n2: Double =
                            _state.value.expression.substring(i + 1, indexNumbers[1]).toDouble()
                        val result: Double = getResult(n1, n2, _state.value.expression[i])
                        _state.value = _state.value.copy(
                            expression = _state.value.expression.substring(
                                0,
                                indexNumbers[0]
                            ) + result.toString() + _state.value.expression.substring(
                                indexNumbers[1],
                                _state.value.expression.length
                            )
                        )
                    }
                }
            }
            else{
                for (i in 1.._state.value.expression.length) {
                    if (_state.value.expression[i] == '+' || _state.value.expression[i] == '-') {
                        val indexNumbers = getNumbers(i)
                        val n1: Double =
                            _state.value.expression.substring(indexNumbers[0], i).toDouble()
                        val n2: Double =
                            _state.value.expression.substring(i + 1, indexNumbers[1]).toDouble()
                        val result: Double = getResult(n1, n2, _state.value.expression[i])
                        _state.value = _state.value.copy(
                            expression = _state.value.expression.substring(
                                0,
                                indexNumbers[0]
                            ) + result.toString() + _state.value.expression.substring(
                                indexNumbers[1],
                                _state.value.expression.length
                            )
                        )
                    }
                }
            }
        }

        /*
        val number1 = _state.value.number1.toDoubleOrNull()
        val number2 = _state.value.number2.toDoubleOrNull()
        var result: Double? = null
        if (number1 != null && number2 != null) {
            when (_state.value.operation) {
                is CalculatorOperation.Add -> result = number1 + number2
                is CalculatorOperation.Subtraction -> result = number1 - number2
                is CalculatorOperation.Multiply -> result = number1 * number2
                is CalculatorOperation.Divide -> result = number1 / number2
                is CalculatorOperation.Exponent -> result = number1.pow(number2)
                null -> return
            }
            _state.value = _state.value.copy(
                number1 = result.toString().take(15),
                number2 = "",
                operation = null
            )
            Log.d("deb", "result:")
            Log.d("deb", result.toString())
            Log.d("deb", (number1).toString())
            Log.d("deb", (number2).toString())
            Log.d("deb", _state.value.operation.toString())

        }
        */




    }

    private fun getNumbers(indexChar: Int): MutableList<Int> {
        val numbers = mutableListOf(0,_state.value.expression.length)
        var index: Int = 0
        for (i in indexChar downTo 0){
            if (!_state.value.expression[i].isDigit() &&  _state.value.expression[i] != '.'){
                index=i
                break
            }
        }
        numbers[0] = index+1

        index = _state.value.expression.length
        for (i in indexChar.._state.value.expression.length){
            if (!_state.value.expression[i].isDigit() &&  _state.value.expression[i] != '.'){
                index=i
                break
            }
        }
        numbers[1] = index
        return(numbers)
    }

    private fun getResult(n1: Double, n2: Double, op: Char): Double {
        val result: Double = when (op) {
            '+' -> n1 + n2
            '-' -> n1 - n2
            '*' -> n1 * n2
            '/' -> n1 / n2
            '^' -> n1.pow(n2)
            else -> return 0.0
        }
        return result
    }

    private fun isDouble(str: String): Boolean {
        return try {
            str.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }



    private fun enterDecimal() {
        if (_state.value.expression.last().isDigit()){
            var find: Boolean = false
            for (i in _state.value.expression.length downTo 0){
                if (_state.value.expression[i] == '.'){
                    find = true
                    break
                }
                if (!_state.value.expression[i].isDigit()){
                    break
                }
            }
            if (!find){
                _state.value = _state.value.copy(
                    expression = _state.value.expression + "."
                )
            }
        }
    }

    private fun enterChar(c: Char) {
        _state.value = _state.value.copy(
            expression = _state.value.expression + c
        )
    }

    companion object {
        private const val MAX_NUM_LENGHT=8
    }



}