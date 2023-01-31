package com.example.customview2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.customview2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var isFirstPlayer = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ticTacToeView.actionListener = { row, column, field ->
            val cell = field.getCell(row, column)
            if (cell == Cell.EMPTY) {
                if (isFirstPlayer) {
                    field.setCell(row, column, Cell.PLAYER_1)
                } else {
                    field.setCell(row, column, Cell.PLAYER_2)
                }
                isFirstPlayer = !isFirstPlayer
            }
        }

        binding.ticTacToeView.field = TicTacToeField(3, 3)
    }
}