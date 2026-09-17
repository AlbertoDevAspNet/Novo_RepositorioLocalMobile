package com.example.calculadora

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    MaterialTheme {
        CalculatorScreen()
    }
}

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = viewModel.displayValue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            fontSize = 64.sp,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Light,
            maxLines = 1
        )

        val buttons = listOf(
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("C", "0", "=", "+")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { char ->
                    CalculatorButton(
                        text = char,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            when (char) {
                                "C" -> viewModel.onClearClick()
                                "=" -> viewModel.onEqualClick()
                                "+", "-", "*", "/" -> viewModel.onOperatorClick(char)
                                else -> viewModel.onNumberClick(char)
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val containerColor = when (text) {
        "=", "+", "-", "*", "/" -> MaterialTheme.colorScheme.primary
        "C" -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.secondaryContainer
    }
    
    val contentColor = when (text) {
        "=", "+", "-", "*", "/", "C" -> Color.White
        else -> MaterialTheme.colorScheme.onSecondaryContainer
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .aspectRatio(1f),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
