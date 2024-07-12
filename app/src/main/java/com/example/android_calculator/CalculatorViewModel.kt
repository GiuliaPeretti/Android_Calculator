package com.example.android_calculator

import android.util.Log
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
            Log.d("deb", "while")
            Log.d("deb", _state.value.expression)

            if (_state.value.expression.contains("x", ignoreCase = true)
                || _state.value.expression.contains("/", ignoreCase = true)
                || _state.value.expression.contains("^", ignoreCase = true)
            ) {
                Log.d("deb", "entra x")
                for (i in 0 until _state.value.expression.length) {
                    if (_state.value.expression[i] == 'x' || _state.value.expression[i] == '/' || _state.value.expression[i] == '^') {
                        _state.value = _state.value.copy(
                            expression = doOperation(exp= _state.value.expression, i= i)
                        )
                        break
                    }
                }
            } else{
                Log.d("deb", "entra + -")
                for (i in 0 until _state.value.expression.length) {
                    if (_state.value.expression[i] == '+' || _state.value.expression[i] == '-') {
                        _state.value = _state.value.copy(
                            expression = doOperation(exp= _state.value.expression, i= i)
                            )
                        break
                    }
                }
            }
        }
    }

    private fun doOperation(exp: String, i: Int): String {
        val indexNumbers = getNumbers(i)
        Log.d("deb", indexNumbers.toString())

        Log.d("deb",exp.substring(indexNumbers[0], i))
        val n1: Double =
            exp.substring(indexNumbers[0], i).toDouble()



        var n2: Double
        if(indexNumbers[1]== exp.length-1){
            n2 =
                exp.substring(i + 1, indexNumbers[1]+1).toDouble()
            Log.d("deb", n2.toString())
        } else {
            n2 =
                exp.substring(i + 1, indexNumbers[1]).toDouble()
            Log.d("deb", n2.toString())
        }

        val result: Double = getResult(n1, n2, exp[i])
        Log.d("deb", result.toString())

        Log.d("deb", exp.substring(indexNumbers[1]+1, exp.length))
        Log.d("deb", exp.substring(0, indexNumbers[0])
                + result.toString()
                + exp.substring(indexNumbers[1], exp.length))

        return if(indexNumbers[1]== exp.length-1) {
            (exp.substring(0, indexNumbers[0])
                    + result.toString()
                    + exp.substring(indexNumbers[1]+1, _state.value.expression.length) )

        }else {
            (exp.substring(0, indexNumbers[0])
                    + result.toString()
                    + exp.substring(indexNumbers[1], _state.value.expression.length))
        }

    }

    private fun getNumbers(indexChar: Int): MutableList<Int> {
        val numbers = mutableListOf(0,_state.value.expression.length-1)
        var index: Int = 0
        for (i in indexChar-1 downTo 0){
            if (!_state.value.expression[i].isDigit() &&  _state.value.expression[i] != '.'){
                index=i+1
                break
            }
        }
        numbers[0] = index

        index = _state.value.expression.length-1
        for (i in indexChar+1 until _state.value.expression.length){
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
            'x' -> n1 * n2
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