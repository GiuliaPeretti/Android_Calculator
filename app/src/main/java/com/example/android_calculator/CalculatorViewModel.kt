package com.example.android_calculator

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.RoundingMode
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan
import kotlin.math.PI


class CalculatorViewModel: ViewModel() {
    private val _state = MutableStateFlow(CalculatorState())
    val state: StateFlow<CalculatorState> = _state.asStateFlow()

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.String -> enterStr(s = action.s)
            is CalculatorAction.Character -> enterChar(c = action.c)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Clear -> performClear()
            is CalculatorAction.Calculate -> CalculateResult(_state.value.expression)
            is CalculatorAction.Delete -> performeDelete()
            is CalculatorAction.ChangeDeg ->changeDeg()
            is CalculatorAction.movePosition -> movePos(p = action.p + _state.value.pos)
        }
    }

    private fun changeDeg() {
        if ( _state.value.deg=="deg"){
            _state.value = _state.value.copy(
                deg = "rad"
            )
        } else {
            _state.value = _state.value.copy(
                deg = "deg"
            )
        }

    }

    private fun performClear() {
        _state.value = _state.value.copy(
            expression = ""
        )
        displayResult()
    }

    private fun performeDelete() {
        clearError()
        _state.value = _state.value.copy(
            expression = _state.value.expression.dropLast(1)
        )
        movePos(_state.value.expression.length)
    }

    private fun performOperation(exp:String): String {
        var expression: String = exp
        while (!isDouble(expression)) {
            if (expression in errorList){
                return(expression)
            }

            if (expression.contains("^", ignoreCase = true)
                || expression.contains("√", ignoreCase = true)
                || expression.contains("!", ignoreCase = true)
                || expression.contains("ln", ignoreCase = true)
                || expression.contains("log", ignoreCase = true)
                || expression.contains("cos", ignoreCase = true)
                || expression.contains("sin", ignoreCase = true)
                || expression.contains("tan", ignoreCase = true)
            ) {
                //TODO: sin(2)cos(3) in questi casi va messo un x in mezzo
                for (i in expression.indices) {
                    if (expression[i] == '^') {
                        expression = doOperation(exp = expression, i = i)
                        break
                    }
                    if (expression[i] == '√') {
                        expression = squareRoot(exp = expression, indexChar = i)
                        break
                    }
                    if (expression[i] == '!') {
                        expression = factorial(exp = expression, indexChar = i)
                        break
                    }
                    if (i+2< expression.length && expression.substring(i, i+2) == "ln"){
                        expression = naturaLogarithm(exp=expression, indexChar=i)
                        break
                    }
                    if (i+3< expression.length && expression.substring(i, i+3) == "log"){
                        expression = Logarithm(exp=expression, indexChar=i)
                        break
                    }
                    if (i+3< expression.length && expression.substring(i, i+3) == "cos"){
                        expression = Cosine(exp=expression, indexChar=i)
                        break
                    }
                    if (i+3< expression.length && expression.substring(i, i+3) == "sin"){
                        expression = Sinus(exp=expression, indexChar=i)
                        break
                    }
                    if (i+3< expression.length && expression.substring(i, i+3) == "tan"){
                        expression = Tangent(exp=expression, indexChar=i)
                        break
                    }
                }
            } else if (expression.contains("x", ignoreCase = true)
                || expression.contains("/", ignoreCase = true)
            ) {
                for (i in expression.indices) {
                    if (expression[i] == 'x' || expression[i] == '/' ) {
                        expression = doOperation(exp = expression, i = i)
                        break
                    }
                }
            } else {
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

        val n1: Double =
            exp.substring(indexNumbers[0], i).toDouble()

        val n2: Double =
            exp.substring(i + 1, indexNumbers[1] + 1).toDouble()

        if(n2==0.0 && exp[i]=='/'){
            return errorList[1]
        }
        val result: Double = getResult(n1, n2, exp[i])
        //TODO("check if result is int")
        return exp.substring(0,indexNumbers[0])+result+exp.substring(indexNumbers[1]+1,exp.length)
    }

    private fun squareRoot(exp: String, indexChar: Int): String {
        var text = exp
        var index = text.length - 1
        for (i in indexChar + 1 until text.length) {  //2x2x2
            if (!text[i].isDigit() && text[i] != '.') {
                index = i-1
                break
            }
        }
        var n = text.substring(indexChar+1, index+1).toDouble()
        if (index!= 0 && text[index-1].isDigit() ){
            return text.substring(0,indexChar)+"x" + sqrt(n).toString() + text.substring(index+1, text.length)
        }
        return text.substring(0,indexChar)+ sqrt(n).toString() + text.substring(index+1, text.length)
    }

    private fun factorial(exp: String, indexChar: Int): String {
        var text = exp
        var index: Int = 0
        for (i in indexChar - 1 downTo 0) {
            if (!text[i].isDigit() && text[i] != '.') {
                index = i + 1
                break
            }
        }

        var n = text.substring(index, indexChar).toDouble()

        var number: Int
        if (isInt(n)){
            number = n.toInt()
        }else{
            //TODO: se il fattoriale non è un int da errore di sintassi, sistemare più avanti
            return errorList[0]
        }
        var result = 1
        for (i in 1..number) {
            result *= i
        }
        return text.substring(0,index) + result.toString() + text.substring(indexChar+1, text.length)
    }

    private fun naturaLogarithm(exp: String, indexChar: Int): String{
        var text = exp
        var index = text.length - 1
        for (i in indexChar + 2 until text.length) {  //2x2x2
            if (!text[i].isDigit() && text[i] != '.') {
                index = i-1
                break
            }
        }
        var n = text.substring(indexChar+2, index+1).toDouble()
        if (indexChar!= 0 && text[indexChar-1].isDigit() ){
            return text.substring(0,indexChar)+"x" + ln(n).toString() + text.substring(index+1, text.length)
        }
        return text.substring(0,indexChar)+ ln(n).toString() + text.substring(index+1, text.length)
    }

    private fun Logarithm(exp: String, indexChar: Int): String{
        var text = exp
        var index = text.length - 1
        for (i in indexChar + 3 until text.length) {  //2x2x2
            if (!text[i].isDigit() && text[i] != '.') {
                index = i-1
                break
            }
        }
        var n = text.substring(indexChar+3, index+1).toDouble()
        if (indexChar!= 0 && text[indexChar-1].isDigit() ){
            return text.substring(0,indexChar)+"x" + log(n, 10.0).toString() + text.substring(index+1, text.length)
        }
        return text.substring(0,indexChar)+ log(n, 10.0).toString() + text.substring(index+1, text.length)
    }

    private fun Cosine(exp: String, indexChar: Int): String{
        var text = exp
        var index = text.length - 1
        for (i in indexChar + 3 until text.length) {  //2x2x2
            if (!text[i].isDigit() && text[i] != '.') {
                index = i-1
                break
            }
        }
        var n = text.substring(indexChar+3, index+1).toDouble()
        if (_state.value.deg=="deg"){
            n = degToRadius(n)
        }
        var result = cos(n)
        result = result.toBigDecimal().setScale(10, RoundingMode.HALF_EVEN).toDouble()
        if (indexChar!= 0 && text[indexChar-1].isDigit() ){
            return text.substring(0,indexChar)+"x" + result + text.substring(index+1, text.length)
        }
        return text.substring(0,indexChar)+ result + text.substring(index+1, text.length)
    }

    private fun Sinus(exp: String, indexChar: Int): String{
        var text = exp
        var index = text.length - 1
        for (i in indexChar + 3 until text.length) {  //2x2x2
            if (!text[i].isDigit() && text[i] != '.') {
                index = i-1
                break
            }
        }
        var n = text.substring(indexChar+3, index+1).toDouble()
        if (_state.value.deg=="deg"){
            n = degToRadius(n)
        }
        var result = sin(n)
        result = result.toBigDecimal().setScale(10, RoundingMode.HALF_EVEN).toDouble()
        if (indexChar!= 0 && text[indexChar-1].isDigit() ){
            return text.substring(0,indexChar)+"x" + result + text.substring(index+1, text.length)
        }
        return text.substring(0,indexChar)+ result.toString() + text.substring(index+1, text.length)
    }

    private fun Tangent(exp: String, indexChar: Int): String{
        var text = exp
        var index = text.length - 1
        for (i in indexChar + 3 until text.length) {  //2x2x2
            if (!text[i].isDigit() && text[i] != '.') {
                index = i-1
                break
            }
        }
        var n = text.substring(indexChar+3, index+1).toDouble()
        if (_state.value.deg=="deg"){
            n = degToRadius(n)
        }
        var result = tan(n)
        result = result.toBigDecimal().setScale(10, RoundingMode.HALF_EVEN).toDouble()
        if (indexChar!= 0 && text[indexChar-1].isDigit() ){
            return text.substring(0,indexChar)+"x" + result + text.substring(index+1, text.length)
        }
        return text.substring(0,indexChar)+ result.toString() + text.substring(index+1, text.length)
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
        for (i in indexChar + 1 until text.length) {  //2x2x2
            if (!text[i].isDigit() && text[i] != '.') {
                index = i-1
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
            return false
        } else {
            return true
        }
    }

    private fun degToRadius(n: Double): Double {
        return (n * (PI / 180))
    }

    private fun radiusToDeg(n: Double): Double {
        return (n * (180 / PI))
    }

    private fun CalculateResult(t: String) {
        var text: String = t
        clearError()
        if (_state.value.expression.isEmpty()){
            return
        }
        if (!isValid(text)){
            _state.value = _state.value.copy(
                expression = errorList[0]
            )
            return
        }
        if (text.contains("π", ignoreCase = true)){
            text=handlePi(text)
        }
        if (text.contains("%", ignoreCase = true)){
            text=handlePercentage(text)
        }

        while ('(' in text) {
            //TODO: togli ()
            val list: MutableList<Int> = insidePar(text)
            val pos1 = list[0]
            val pos2 = list[1]
            var num: String
            if (isDouble(text.substring( pos1 + 1,pos2))) {
                Log.d("deb", "dentro la parentesi è num")
                num = text.substring( pos1 + 1,pos2)

                if(pos1 - 1 != -1 && pos2 + 1 != text.length
                    && (isDouble(text[pos1-1]) ||text[pos1-1].equals(')')  )
                    && (isDouble(text[pos2+1]) || text[pos2+1].equals('(')   )
                    )
                {
                    text = text.substring(0, pos1) + "x" + (num) + 'x' + text.substring(pos2 + 1, text.length)

                }else if (pos1 - 1 != -1 && ( isDouble(text[pos1-1]) || text[pos1-1].equals(')') )) {
                    if (pos2  == text.length - 1) {
                        text = text.substring(0, pos1) + "x" + (num)
                    } else {
                        text = text.substring(0, pos1) + "x" + (num) + text.substring(pos2 + 1, text.length)
                    }
                }else if(pos2 + 1 != text.length && (isDouble(text[pos2+1]) || text[pos2+1].equals('(') ) ) {
                    text = text.substring(0, pos1) + num + 'x' + text.substring(pos2 + 1, text.length)
                }else {
                    if (pos2+1 == text.length){
                        text = text.substring(0,pos1)+(num)
                    } else {
                        text = text.substring(0, pos1) + (num) + text.substring(pos2 + 1, text.length)
                    }
                }
            }
            else {
                Log.d("deb", text)
                val te=text.substring(pos1+1,pos2)
                Log.d("deb", te)
                val r=performOperation(te)
                if (r in errorList){
                    text=r
                }else{
                    text=text.substring(0,pos1+1)+r+text.substring(pos2,text.length)
                }
            }
        }

        val r=performOperation(text)
        _state.value = _state.value.copy(
            expression = r
        )
        displayResult()
    }

    private fun handlePi(t: String): String {
        var text= t
        while("π" in text){
            val index=text.indexOf("π")
            if (index!=0 && index!=text.length-1 && text[index-1]!='(' && text[index+1]!=')' && !isOperator(text[index-1]) && !isOperator(text[index+1])      ){
                text = text.substring(0,index) + PI + text.substring(index+1, text.length)
                break
            }
            if (index!=0  && text[index-1]!='(' && !isOperator(text[index-1]) ){
                text = text.substring(0,index) + "x" + PI  + text.substring(index+1, text.length)
                break
            }
            if (index!=text.length-1 && text[index+1]!=')' && !isOperator(text[index+1]) ){
                text = text.substring(0,index) + PI + "x" + text.substring(index+1, text.length)
                break
            }
            text = text.substring(0,index) + PI + text.substring(index+1, text.length)
        }
        return text
    }

    private fun handlePercentage(t: String): String {
        var text= t
        while("%" in text){
            val index=text.indexOf("%")
            if (index!=text.length-1 && text[index+1]!=')' && !isOperator(text[index+1]) ){
                text = text.substring(0,index) + "/100x" + text.substring(index+1, text.length)
                break
            }
            text = text.substring(0,index) + "/100" + text.substring(index+1, text.length)
        }
        return text
    }

    private fun insidePar(t: String): MutableList<Int> { //(34(23*2)3(23))
        var text: String = t
        var pos1=text.indexOf('(')
        var pos2=text.indexOf(')')
        val l = mutableListOf<Int>(text.indexOf('('),text.indexOf(')'))
        text=text.substring(0,l[0])+'['+text.substring(l[0]+1,l[1])+']'+text.substring(l[1]+1,text.length)
        while('(' in text.substring(0,l[1])) {
            pos1=text.indexOf('(')
            l[0] = text.indexOf('(')
            text = text.substring(0,l[0])+'['+text.substring(l[0]+1,text.length)
        }
        return(l)
    }
    //private fun resolveBrackets

    private fun isValid(t: String): Boolean{
        //TODO: aggiungi regole per %, pi lg, ln ecc e sqrt
        var countPar = 0
        if (isOperator(t[0]) || isOperator(t[t.length-1])){
            return false
        }
        var op = false
        for (i in t){
            op = if (isOperator(i)){
                if (op){
                    return false
                }else{
                    true
                }
            }else{
                false
            }
            if (i=='(') {
                countPar += 1
            } else if(i==')'){
                countPar -= 1
            }
            if(countPar<0){
                return false
            }
        }
        if (countPar!=0){
            return false
        }
        return true
    }

    private fun isOperator(c: Char): Boolean{
        //TODO: forse aggiungi quelli nuovi
        if (c=='+' || c=='-' || c=='x' || c=='/' || c=='^'){
            return true
        }
        return false
    }

    private fun enterDecimal() {
        clearError()
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
        movePos(_state.value.expression.length)
    }

    private fun enterChar(c: Char) {
        clearError()
        _state.value = _state.value.copy(
            expression = _state.value.expression + c
        )
        movePos(_state.value.expression.length)
    }

    private fun enterStr(s: String) {
        clearError()
        _state.value = _state.value.copy(
            expression = _state.value.expression + s
        )
        movePos(_state.value.expression.length)
    }

    private fun clearError(){
        if (_state.value.expression in errorList){
            _state.value = _state.value.copy(
                expression = ""
            )
        }
        movePos(_state.value.expression.length)
    }

    private fun displayResult(){
        if (_state.value.expression.length<=10){
            _state.value = _state.value.copy(
                displayed = _state.value.expression
            )
            _state.value = _state.value.copy(
                pos = _state.value.expression.length-1
            )
        } else {
            _state.value = _state.value.copy(
                displayed = _state.value.expression.substring(0, 10)+"_"
            )
            _state.value = _state.value.copy(
                pos = 10
            )
        }

    }

    private fun movePos(p: Int){
        if(p>=0 && p<=_state.value.expression.length){
            _state.value = _state.value.copy(
                pos = p
            )
            if(p>15){
                if (_state.value.pos==_state.value.expression.length){
                    _state.value = _state.value.copy(
                        displayed = "_"+_state.value.expression.substring(_state.value.pos-15, _state.value.pos)
                    )
                }else{
                    _state.value = _state.value.copy(
                        displayed = "_"+_state.value.expression.substring(_state.value.pos-15, _state.value.pos)+"_"
                    )
                }
            }else {
                if (_state.value.expression.length< 15 ){
                    _state.value = _state.value.copy(
                        displayed = _state.value.expression.substring(0,_state.value.expression.length)
                    )
                } else {
                    _state.value = _state.value.copy(
                        displayed = _state.value.expression.substring(0, 15)+"_"
                    )
                }
            }
        }

    }

    companion object {
        private const val MAX_NUM_LENGHT=8
        private val errorList = arrayOf("SyntaxError", "divisionByZero")
    }

}