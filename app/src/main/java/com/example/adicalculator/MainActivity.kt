package com.example.adicalculator

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adicalculator.ui.theme.AdiCalculatorTheme
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdiCalculatorTheme {

                val viewModel: CalculatorViewModel by viewModels()

                var numberLabel by remember {
                    mutableStateOf("0")
                }

                var invAdd by remember {
                    mutableStateOf(false)
                }

                var invSub by remember {
                    mutableStateOf(false)
                }

                var invMul by remember {
                    mutableStateOf(false)
                }

                var invDiv by remember {
                    mutableStateOf(false)
                }


                fun handleNumberClick(number: String) {

                    if(invMul || invDiv || invAdd || invSub){
                        numberLabel = number
                        invMul = false
                        invAdd = false
                        invSub = false
                        invDiv = false
                    } else {
                        numberLabel = when (number) {
                            "." -> {
                                "$numberLabel."
                            }
                            else -> {
                                if (numberLabel.toDouble() == 0.0) {
                                    number
                                } else if (numberLabel.length >= 7){
                                    numberLabel
                                } else {
                                    numberLabel + number
                                }
                            }
                        }
                    }


                }

                fun handleSpecialClick(symbol: String) {
                    when (symbol) {
                        "AC" -> {
                            viewModel.clear()
                            numberLabel = "0"
                        }
                        "+/-" -> {
                            var number = numberLabel.toDouble()
                            number = -(number)
                            numberLabel = number.toString()
                        }
                        "Del" -> {
                            if(numberLabel.length > 1) {
                                val str = numberLabel.dropLast(1)
                                numberLabel = str
                            } else {
                                numberLabel = "0"
                            }
                        }
                    }
                }

                fun handleOperationClick(operation: String) {
                    // Store current number and operation
                    viewModel.setFirstNum(numberLabel.toDouble())
                    viewModel.setOperation(operation)
                    when (operation) {
                        "×" -> {
                            invMul = true
                        }
                        "÷" -> {
                            invDiv = true
                        }
                        "+" -> {
                            invAdd = true
                        }
                        "-" -> {
                            invSub = true
                        }
                    }
                }

                fun handleEqualsClick() {
                    val result = viewModel.calculate(numberLabel.toDouble())
                    numberLabel = result.toString()
                    invAdd = false
                    invSub = false
                    invDiv = false
                    invMul = false
                }

                fun changeColor(condition: Boolean): Color {
                    return if(condition) {
                        Color.LightGray
                    } else {
                        Color(0xFFFFA500)
                    }
                }


                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black)
                ) {


                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .padding(16.dp, 0.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = numberLabel,
                            fontSize = 70.sp,
                            color = Color.White
                        )
                    }


                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        CalculatorButton(
                            symbol = "AC",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = Color.LightGray, shape = CircleShape),
                            onClick = { handleSpecialClick("AC") }
                        )

                        CalculatorButton(
                            symbol = "+/-",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = Color.LightGray, shape = CircleShape),
                            onClick = { handleSpecialClick("+/-") }
                        )

                        CalculatorButton(
                            symbol = "Del",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = Color.LightGray, shape = CircleShape),
                            onClick = { handleSpecialClick("Del") }
                        )

                        CalculatorButton(
                            symbol = "÷",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = changeColor(invDiv), shape = CircleShape),
                            onClick = { handleOperationClick("÷") }
                        )

                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        CalculatorButton(
                            symbol = "7",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = Color.DarkGray, shape = CircleShape),
                            onClick = { handleNumberClick("7") }
                        )
                        CalculatorButton(
                            symbol = "8",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = Color.DarkGray, shape = CircleShape),
                            onClick = { handleNumberClick("8") }
                        )
                        CalculatorButton(
                            symbol = "9",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = Color.DarkGray, shape = CircleShape),
                            onClick = { handleNumberClick("9") }
                        )
                        CalculatorButton(
                            symbol = "×",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = changeColor(invMul), shape = CircleShape),
                            onClick = { handleOperationClick("×") }
                        )

                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        CalculatorButton(
                            symbol = "4",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = Color.DarkGray, shape = CircleShape),
                            onClick = { handleNumberClick("4") }
                        )
                        CalculatorButton(
                            symbol = "5",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = Color.DarkGray, shape = CircleShape),
                            onClick = { handleNumberClick("5") }
                        )
                        CalculatorButton(
                            symbol = "6",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = Color.DarkGray, shape = CircleShape),
                            onClick = { handleNumberClick("6") }
                        )
                        CalculatorButton(
                            symbol = "-",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = changeColor(invSub), shape = CircleShape),
                            onClick = { handleOperationClick("-") }
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        CalculatorButton(
                            symbol = "1",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = Color.DarkGray, shape = CircleShape),
                            onClick = { handleNumberClick("1") }
                        )
                        CalculatorButton(
                            symbol = "2",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = Color.DarkGray, shape = CircleShape),
                            onClick = { handleNumberClick("2") }
                        )
                        CalculatorButton(
                            symbol = "3",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = Color.DarkGray, shape = CircleShape),
                            onClick = { handleNumberClick("3") }
                        )
                        CalculatorButton(
                            symbol = "+",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = changeColor(invAdd), shape = CircleShape),
                            onClick = { handleOperationClick("+") }
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        CalculatorButton(
                            symbol = "0",
                            modifier = Modifier
                                .size(170.dp, 80.dp)
                                .background(color = Color.DarkGray, shape = CircleShape),
                            onClick = { handleNumberClick("0") }
                        )
                        CalculatorButton(
                            symbol = ".",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = Color.DarkGray, shape = CircleShape),
                            onClick = { handleNumberClick(".") }
                        )
                        CalculatorButton(
                            symbol = "=",
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = Color(0xFFFFA500), shape = CircleShape),
                            onClick = { handleEqualsClick() }
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .height(30.dp)
                    )

                }
            }
        }
    }
}

