package com.example.adicalculator

import androidx.lifecycle.ViewModel
import kotlin.math.round

class CalculatorViewModel: ViewModel() {
    private var firstNumber:Double? = null
    private var operation: String? = null

    fun setFirstNum(number:Double) {
        firstNumber = number
    }

    fun setOperation(op: String) {
        operation = op
    }

    fun clear() {
        firstNumber = null
        operation = null
    }

    fun calculate(secondNumber: Double): Double  {
        if (firstNumber == null && operation == null) {
            return secondNumber
        }

        return when(operation) {
            "+" -> {
                firstNumber!! + secondNumber
            }
            "-" -> {
                firstNumber!! - secondNumber
            }
            "×" -> {
                firstNumber!! * secondNumber
            }
            "÷" -> {
                String.format("%.2f", firstNumber!! / secondNumber).toDouble()
            }
            else -> {
                secondNumber
            }
        }
    }
}