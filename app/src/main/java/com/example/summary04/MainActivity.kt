package com.example.summary04

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.summary04.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val buttonsArray = Array(3) { arrayOfNulls<Button>(3) }

    private var player1Turn = true

    private var roundCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        for (row in 0..2) {
            for (column in 0..2) {
                val buttonID = "button_$row$column"
                val resID: Int = resources.getIdentifier(buttonID, "id", packageName)
                buttonsArray[row][column] = findViewById(resID)
                buttonsArray[row][column]!!.setOnClickListener(this)
            }
        }

        binding.buttonReset.setOnClickListener { resetBoard() }
    }

    override fun onClick(v: View) {
        if ((v as Button).text.toString() != "") {
            return
        }

        if (player1Turn) {
            v.text = "X"
        } else {
            v.text = "O"
        }

        roundCount++

        if (roundCount > 4) {
            if (checkForWin()) {
                if (player1Turn) {
                    gameFinished("Player 1 Wins")
                } else {
                    gameFinished("Player 2 Wins")
                }
            }
        } else if (roundCount == 9) {
            gameFinished("Draw !")
        } else {
            player1Turn = !player1Turn
        }

    }

    private fun checkForWin(): Boolean {
        val field = Array(3) { arrayOfNulls<String>(3) }

        for (row in 0..2) {
            for (column in 0..2) {
                field[row][column] = buttonsArray[row][column]!!.text.toString()
            }
        }

        // Check Rows
        for (row in 0..2) {
            if (field[row][0] == field[row][1] && field[row][0] == field[row][2] && field[row][0] != "") {
                return true
            }
        }

        // Check Columns
        for (column in 0..2) {
            if (field[0][column] == field[1][column] && field[0][column] == field[2][column] && field[0][column] != "") {
                return true
            }
        }

        // Check Diagonals
        if (field[0][0] == field[1][1] && field[0][0] == field[2][2] && field[0][0] != "") {
            return true
        }

        return field[0][2] == field[1][1] && field[0][2] == field[2][0] && field[0][2] != ""
    }

    private fun gameFinished(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

        resetBoard()
    }

    private fun resetBoard() {
        for (row in 0..2) {
            for (column in 0..2) {
                buttonsArray[row][column]!!.text = ""
            }
        }

        roundCount = 0
        player1Turn = true
    }

}