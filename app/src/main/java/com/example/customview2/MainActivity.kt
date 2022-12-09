package com.example.customview2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.customview2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ticTacToeView.ticTacToeField = TicTacToeField(10, 10)
        binding.btnRandom.setOnClickListener {
        }
    }
}