package com.example.android_calculator

import android.util.Log
import androidx.compose.runtime.collectAsState
import  androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel: ViewModel() {
    private val _state = MutableStateFlow(CalculatorState())
    val state: StateFlow<CalculatorState> = _state.asStateFlow()

    fun onAction(action: CalculatorAction) {
        when(action){
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Clear -> performClear()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performOperation()
            is CalculatorAction.Delete -> performeDelete()
        }
    }

    private fun performClear() {
        _state.value = CalculatorState()
    }

    private fun performeDelete() {
        when {
            _state.value.number2.isNotBlank() -> _state.value=_state.value.copy(
                number2 = _state.value.number2.dropLast(1)
            )
            _state.value.operation != null -> _state.value=_state.value.copy(
                operation = null
            )
            _state.value.number1.isNotBlank() -> _state.value=_state.value.copy(
                number1 = _state.value.number1.dropLast(1)
            )
        }
    }

    private fun performOperation() {
        val number1 = _state.value.number1.toDoubleOrNull()
        val number2 = _state.value.number2.toDoubleOrNull()
        var result: Double? = null
        if (number1 != null && number2 != null) {
            when (_state.value.operation) {
                is CalculatorOperation.Add -> result = number1 + number2
                is CalculatorOperation.Subtraction -> result = number1 - number2
                is CalculatorOperation.Multiply -> result = number1 * number2
                is CalculatorOperation.Divide -> result = number1 / number2
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



    }

    private fun enterOperation(operation: CalculatorOperation) {
        if(_state.value.number1.isNotBlank()) {
            _state.value = _state.value.copy(operation = operation)
        }
    }

    private fun enterDecimal() {
        if (_state.value.operation == null && !_state.value.number1.contains(".") && _state.value.number1.isNotBlank()){
            _state.value = _state.value.copy(
                number1 = _state.value.number1 +"."
            )
            return
        }
        if (_state.value.operation == null && _state.value.number2.isBlank()){
            _state.value = _state.value.copy(
                number1 = "0."
            )
            return
        }
        if (!_state.value.number2.contains(".") && _state.value.number2.isNotBlank()){
            _state.value = _state.value.copy(
                number2 = _state.value.number2 +"."
            )
            return
        }
        if (_state.value.number2.isBlank()){
            _state.value = _state.value.copy(
                number2 = "0."
            )
            return
        }
    }

    private fun enterNumber(number: Int) {
        if (_state.value.operation == null) {
            if (_state.value.number1.length >= MAX_NUM_LENGHT){
                return
            }
            _state.value = _state.value.copy(
                number1 = _state.value.number1 + number.toString()
            )
            return
        }
        if (_state.value.number2.length >= MAX_NUM_LENGHT){
            return
        }
        _state.value = _state.value.copy(
            number2 = _state.value.number2 + number
        )
    }

    companion object {
        private const val MAX_NUM_LENGHT=8
    }



}