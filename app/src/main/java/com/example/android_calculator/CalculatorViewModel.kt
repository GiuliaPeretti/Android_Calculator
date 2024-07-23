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
        when (action) {
            is CalculatorAction.Character -> enterChar(c = action.c)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Clear -> performClear()
            is CalculatorAction.Calculate -> CalculateResult(_state.value.expression)
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

    private fun performOperation(exp:String): String {
        var expression: String = exp

        while (!isDouble(expression)) {
            Log.d("deb", "while")
            Log.d("deb", expression)



            if (expression.contains("^", ignoreCase = true)
            ) {
                Log.d("deb", "entra ^")
                for (i in expression.indices) {
                    if (expression[i] == '^') {
                        expression = doOperation(exp = expression, i = i)
                        break
                    }
                }
            } else if (expression.contains("x", ignoreCase = true)
                || expression.contains("/", ignoreCase = true)
                || expression.contains("^", ignoreCase = true)
            ) {
                Log.d("deb", "entra x")
                for (i in expression.indices) {
                    if (expression[i] == 'x' || expression[i] == '/' || expression[i] == '^') {
                        expression = doOperation(exp = expression, i = i)
                        break
                    }
                }
            } else {
                Log.d("deb", "entra + -")
                for (i in expression.indices) {
                    if (expression[i] == '+' || expression[i] == '-') {
                        expression = doOperation(exp = expression, i = i)
                        break
                    }
                }
            }
        }
        return(expression)
    }

    private fun doOperation(exp: String, i: Int): String {
        val indexNumbers = getNumbers(i, exp)
        Log.d("deb", indexNumbers.toString())

        Log.d("deb", exp.substring(indexNumbers[0], i))
        val n1: Double =
            exp.substring(indexNumbers[0], i).toDouble()


        var n2: Double
        if (indexNumbers[1] == exp.length - 1) {
            n2 =
                exp.substring(i + 1, indexNumbers[1] + 1).toDouble()
            Log.d("deb", n2.toString())
        } else {
            n2 =
                exp.substring(i + 1, indexNumbers[1]).toDouble()
            Log.d("deb", n2.toString())
        }

        val result: Double = getResult(n1, n2, exp[i])
        //TODO("check if result is int")
        Log.d("deb", result.toString())

        Log.d("deb", exp.substring(indexNumbers[1] + 1, exp.length))


        return exp.substring(0,indexNumbers[0])+result+exp.substring(indexNumbers[1]+1,exp.length)

    }

    private fun getNumbers(indexChar: Int, t: String): MutableList<Int> {
        var text: String = t
        val numbers = mutableListOf(0, text.length - 1)
        var index: Int = 0
        for (i in indexChar - 1 downTo 0) {
            if (!text[i].isDigit() && text[i] != '.') {
                index = i + 1
                break
            }
        }
        numbers[0] = index

        index = text.length - 1
        for (i in indexChar + 1 until text.length) {
            if (!text[i].isDigit() && text[i] != '.') {
                index = i
                break
            }
        }
        numbers[1] = index
        return (numbers)
    }

    private fun getResult(n1: Double, n2: Double, op: Char): Double {
        val result: Double = when (op) {
            '+' -> n1 + n2
            '-' -> n1 - n2
            'x' -> n1 * n2
            '/' -> n1 / n2
            '^' -> n1.times(n2)
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

    private fun isDouble(str: Char): Boolean {
        val str: String = str.toString()
        return try {
            str.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun isInt(n: Double): Boolean {
        if (n - n.toInt() > 0) {
            return (true)
        } else {
            return (false)
        }
    }

    private fun CalculateResult(t: String) {
        var text: String = t
        /*if (text[0] == '(' && text[text.length - 1] == ')') {
            text = text.substring(1, text.length - 1)
        }*/
        while ('(' in text) {
            Log.d("deb", "entra in (")
            val list: MutableList<Int> = insidePar(text)
            val pos1 = list[0]
            val pos2 = list[1]
            var num: String
            if (isDouble(text.substring( pos1 + 1,pos2))) {
                Log.d("deb", "dentro la parentesi è num")
                num = text.substring( pos1 + 1,pos2)

                if(pos1 - 1 != -1 && pos2 + 1 != text.length
                    && (isDouble(text[pos1]) || text[pos1].equals('(') || text[pos1].equals(')')  )
                    && (isDouble(text[pos2]) || text[pos2].equals('(') || text[pos2].equals(')')  )
                    )
                {
                    text = text.substring(0, pos1) + "x" + (num) + 'x' + text.substring(pos2 + 1, text.length)

                }else if (pos1 - 1 != -1 && (isDouble(text.substring( pos1 - 1,pos1)) || text.substring( pos1 - 1,pos1).equals('(') || text.substring( pos1 - 1,pos1).equals(')') )) {
                    if (pos2  == text.length - 1) {
                        text = text.substring(0, pos1) + "x" + (num)
                    } else {
                        text = text.substring(0, pos1) + "x" + (num) + text.substring(pos2 + 1, text.length)
                    }
                }else if(pos2 + 1 != text.length && (isDouble(text.substring(pos2 + 1,pos2+2)) || text.substring(pos2 + 1,pos2+2).equals('(') || text.substring(pos2 + 1,pos2+2).equals(')'))      ) {
                    text = text.substring(0, pos1) + num + 'x' + text.substring(pos2 + 1, text.length)
                }else {
                    text = text.substring(0,pos1)+(num)+text.substring(pos2+1,text.length)
                }
            }
            else {
                Log.d("deb", text)
                val te=text.substring(pos1+1,pos2)
                Log.d("deb", te)
                val r=performOperation(te)
                text=text.substring(0,pos1+1)+r+text.substring(pos2,text.length)
            }
        }

        val r=performOperation(text)
        _state.value = _state.value.copy(
            expression = r
        )


    }

    private fun insidePar(t: String): MutableList<Int> { //(34(23*2)3(23))
        var text: String = t
        var l = mutableListOf<Int>(text.indexOf('('),text.indexOf((')')))
        text=text.substring(l[0]+1,text.indexOf(')'))
        while('(' in text) {
            l[0] = text.indexOf('(')
            text = text.substring(l[0] + 1, text.length)
        }
        return(l)
    }
    //private fun resolveBrackets

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