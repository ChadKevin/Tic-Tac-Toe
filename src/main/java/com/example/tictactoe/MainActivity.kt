package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.ui.theme.TictactoeTheme
val shape = RoundedCornerShape(20.dp)
var winningLine: List<Int>? = null
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TictactoeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                    ) {
                        Greeting(name = "Kevin")
                        Grid()
                    }
                }
            }
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(modifier = Modifier
        .padding(0.dp)
        .background(Color.Black)
        .height(100.dp)
        .fillMaxWidth(),
        contentAlignment = Alignment.Center
    )
    {
        Text(
            text = "Hello, Welcome $name!",
            color = Color.White,
            fontSize = 35.sp
        )
    }
}
fun checkWinner(grid: List<Int>): Int {
    val winningLines = listOf(
        // Rows
        listOf(0, 1, 2),
        listOf(3, 4, 5),
        listOf(6, 7, 8),
        // Columns
        listOf(0, 3, 6),
        listOf(1, 4, 7),
        listOf(2, 5, 8),
        // Diagonals
        listOf(0, 4, 8),
        listOf(2, 4, 6)
    )
    for (line in winningLines) {
        val (a, b, c) = line
        if (grid[a] != 0 && grid[a] == grid[b] && grid[a] == grid[c]) {
            winningLine = line
            return grid[a] // Returns 1 (X wins) or 2 (O wins)
        }
    }
    return 0 // No winner
}
@Composable
fun scoreboard(modifier: Modifier = Modifier){
    Row(){

    }
}

@Composable
fun Grid(modifier: Modifier = Modifier) {
    var xturn by remember { mutableStateOf(true) }
    var winner by remember { mutableStateOf(0) }
    val grid = remember {
        mutableStateListOf(
            0, 0, 0,
            0, 0, 0,
            0, 0, 0
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(40.dp),
        ) {
            for (i in 0..2) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    for (j in 0..2) {
                        val index = i * 3 + j
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(shape)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xFFFFA726), // Light orange
                                            Color(0xFFF57C00)  // Dark orange
                                        )
                                    ),shape
                                )
                                .border(2.dp, Color.Black)
                                .clickable(enabled = grid[index] == 0 && winner == 0) {
                                    if (grid[index] == 0 && winner == 0) {
                                        grid[index] = if (xturn) 1 else 2
                                        winner = checkWinner(grid)
                                        if (winner == 0) {
                                            xturn = !xturn
                                        }
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            when (grid[index]) {
                                1, 2 -> {
                                    if (winningLine?.contains(index) == true){
                                        Image(
                                            painter = painterResource(id = R.drawable.heart_symbol),
                                            contentDescription = "Heart",
                                            modifier = Modifier.fillMaxSize(0.9f)
                                        )
                                    }else{
                                        Image(
                                            painter = painterResource(
                                                id = if (grid[index] == 1) R.drawable.x_symbol else R.drawable.o_symbol
                                            ),
                                            contentDescription = if (grid[index] == 1) "X" else "O",
                                            modifier = Modifier.fillMaxSize(0.9f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (winner != 0) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(shape)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFFFA726), // Light orange
                                        Color(0xFFF57C00)  // Dark orange
                                    )
                                ),shape
                            )
                            .padding(horizontal = 24.dp, vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (winner == 1) "X Wins!" else "O Wins!",
                            color = Color.Black,
                            fontSize = 28.sp
                        )
                    }
                }
            }
            Button (
                onClick = {
                    for (i in grid.indices) {
                        grid[i] = 0
                    }
                    winner = 0
                    xturn = true
                    winningLine = null
                },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .height(60.dp)
                    .width(180.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Refresh",
                    fontSize = 25.sp,
                )
            }
        }
    }
}