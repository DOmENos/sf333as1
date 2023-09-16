package com.example.numberguessinggame


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.numberguessinggame.ui.theme.NumberGuessingGameTheme
import kotlin.random.Random

var solution = Random.nextInt(1,1001)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuessingGameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    NumberGuessingGameLayout()
                }
            }
        }
    }
}

@Composable
fun NumberGuessingGameLayout() {
    var hint by remember { mutableStateOf("Guess now!") }
    val textStart = remember { mutableStateOf("") }
    var isCorrectGuess by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(40.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF102C49))
                .padding(5.dp)


        ) {
            Text(
                text = "Number Guessing Game",
                modifier = Modifier
                    .align(Alignment.Center),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        Text("Try to guess the number I'm thinking of from 1 - 1000!")
        Spacer(modifier = Modifier.height(15.dp))

        InputNumberField(
            textStart = textStart,
            onValueChange = {
                textStart.value = it
                hint = "Guess now!"
            },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )

        Text("Hint: $hint")

        if (isCorrectGuess) {
            Button(onClick = {
                textStart.value = ""
                hint = "Guess now!"
                isCorrectGuess = false
                solution = Random.nextInt(1, 1001)
            }) {
                Text("Play Again")
            }
        } else {
            Button(onClick = {
                val amount = textStart.value.toDoubleOrNull() ?: 0.0
                hint = checkAnswer(amount)
                if (hint == "Correct!") {
                    isCorrectGuess = true
                }
            }) {
                Text("Guess")
            }
        }

        Spacer(modifier = Modifier.height(150.dp))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputNumberField(
    textStart: MutableState<String>,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = textStart.value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(stringResource(R.string.guessing)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}

private fun checkAnswer(amount: Double): String {
    if (amount == 0.0) {
        return "Guess now!"
    }

    if (amount > solution) {
        return "Too high"
    } else if (amount < solution) {
        return "Too low"
    } else if (amount.toInt() == solution) {
        return "Correct!"
    }
    return ""
}

@Preview(showBackground = true)
@Composable
fun NumberGuessingGamePreview() {
    NumberGuessingGameTheme {
        NumberGuessingGameLayout()
    }
}