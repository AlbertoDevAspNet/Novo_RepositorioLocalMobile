package com.example.calculadora

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    var displayValue by mutableStateOf("0")
        private set

    private var firstOperand: Double? = null
    private var operator: String? = null
    private var isNewInput = true

    fun onNumberClick(number: String) {
        if (isNewInput || displayValue == "0") {
            displayValue = number
            isNewInput = false
        } else {
            displayValue += number
        }
    }

    fun onOperatorClick(op: String) {
        firstOperand = displayValue.toDoubleOrNull()
        operator = op
        isNewInput = true
    }

    fun onClearClick() {
        displayValue = "0"
        firstOperand = null
        operator = null
        isNewInput = true
    }

    fun onEqualClick() {
        val secondOperand = displayValue.toDoubleOrNull()
        if (firstOperand != null && operator != null && secondOperand != null) {
            val result = when (operator) {
                "+" -> firstOperand!! + secondOperand
                "-" -> firstOperand!! - secondOperand
                "*" -> firstOperand!! * secondOperand
                "/" -> if (secondOperand != 0.0) firstOperand!! / secondOperand else Double.NaN
                else -> secondOperand
            }
            displayValue = formatResult(result)
            firstOperand = null
            operator = null
            isNewInput = true
        }
    }

    private fun formatResult(result: Double): String {
        return if (result.isNaN()) {
            "Error"
        } else if (result % 1.0 == 0.0) {
            result.toLong().toString()
        } else {
            result.toString()
        }
    }
}
