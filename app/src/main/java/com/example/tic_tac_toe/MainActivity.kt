package com.example.tic_tac_toe

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buttonIsClicked(view: View){
        val bPushed = view as Button
        var cellID = ""
        when(bPushed.id){
            R.id.b1 -> cellID = "A1"
            R.id.b2 -> cellID = "A2"
            R.id.b3 -> cellID = "A3"
            R.id.b4 -> cellID = "B1"
            R.id.b5 -> cellID = "B2"
            R.id.b6 -> cellID = "B3"
            R.id.b7 -> cellID = "C1"
            R.id.b8 -> cellID = "C2"
            R.id.b9 -> cellID = "C3"
            R.id.reset_button -> cellID = "reset"
            R.id.back_button -> cellID = "back"
        }

        playGame(cellID, bPushed)
    }

    var player_X = ArrayList<String>()
    var player_O = ArrayList<String>()
    var activePlayer = 1

    private fun playGame(cellID: String, button: Button) {

        if(cellID == "back") {
            val intent= Intent(this, StartActivity::class.java)
            startActivity(intent)
            return
        }

        if(cellID == "reset") {
            restartGame()
            return
        }

        if(activePlayer == 1) {
            setTurnText("Turn: O")
            Log.i("Turn", "Turn text set to X")
            button.text = "X"
            player_X.add(cellID)
            activePlayer = 2
            Log.i("Turn", "X makes a turn")

        }
        else {
            setTurnText("Turn: X")
            Log.i("Turn", "Turn text set to O")
            button.text = "O"
            player_O.add(cellID)
            activePlayer = 1
            Log.i("Turn", "O makes a turn")
        }

        button.isEnabled = false;

        checkWinner()
        if(isBoardFull()) {
            Toast.makeText(this,"Draw!", Toast.LENGTH_LONG).show()
            clearBoard()
        }
    }

    private fun setTurnText(char: String) {

        val turnText: TextView = findViewById(R.id.turnText)
        turnText.text = char

    }

    @SuppressLint("SetTextI18n")
    private fun addXScoreText() {

        val XScoreText: TextView = findViewById(R.id.XScoreText)
        XScoreText.text = (
                "X score: " +
                        (XScoreText.text.split(" ")
                            .toTypedArray()[2].toInt() + 1).toString()
                )
        Log.i("Score", "X score updated")
    }

    @SuppressLint("SetTextI18n")
    private fun addOScoreText() {

        val OScoreText: TextView = findViewById(R.id.OScoreText)
        OScoreText.text = (
                "O score: " +
                        (OScoreText.text.split(" ")
                            .toTypedArray()[2].toInt() + 1).toString()
                )
        Log.i("Score", "O score updated")
    }

    private fun getButtons(): ArrayList<Button>{
        val buttons = ArrayList<Button>()
        buttons.add(findViewById(R.id.b1))
        buttons.add(findViewById(R.id.b2))
        buttons.add(findViewById(R.id.b3))
        buttons.add(findViewById(R.id.b4))
        buttons.add(findViewById(R.id.b5))
        buttons.add(findViewById(R.id.b6))
        buttons.add(findViewById(R.id.b7))
        buttons.add(findViewById(R.id.b8))
        buttons.add(findViewById(R.id.b9))
        return buttons
    }

    private fun isBoardFull(): Boolean {

        for (button in getButtons()){
            if (button.text != "") {
                continue
            }
            return false
        }
        return true

    }

    private fun clearBoard()
    {
        for (button in getButtons()){
            button.text=""
            button.isEnabled=true
        }

        player_X.clear()
        player_O.clear()
        Log.i("Board", "Board is cleaned")
    }


    @SuppressLint("SetTextI18n")
    private fun restartGame()
    {
        clearBoard()
        val XScoreText: TextView = findViewById(R.id.XScoreText)
        val OScoreText: TextView = findViewById(R.id.OScoreText)
        XScoreText.text = "X score: 0"
        OScoreText.text = "O score: 0"
        Log.i("Game", "Game is restarted")
    }

    private fun checkRow(row: ArrayList<String>): Boolean {
        if(row.contains("A1") and row.contains("A2") and row.contains("A3")
            || row.contains("B1") and row.contains("B2") and row.contains("B3")
            || row.contains("C1") and row.contains("C2") and row.contains("C3"))
            {
            return true
        }
        return false
    }

    private fun checkColumn(column: ArrayList<String>): Boolean {
        if(column.contains("A1") and column.contains("B1") and column.contains("C1")
            || column.contains("A2") and column.contains("B2") and column.contains("C2")
            || column.contains("A3") and column.contains("B3") and column.contains("C3"))
        {
            return true
        }
        return false
    }

    private fun checkDiag(diag: ArrayList<String>): Boolean {
        if(diag.contains("A1") and diag.contains("B2") and diag.contains("C3")
            || diag.contains("C1") and diag.contains("B2") and diag.contains("A3"))
        {
            return true
        }
        return false
    }


    private fun checkWinner() {
        var winner = ""
        if (checkRow(player_X) || checkColumn(player_X) || checkDiag(player_X)) {
            winner = "X"
        }
        else if (checkRow(player_O) || checkColumn(player_O) || checkDiag(player_O)) {
            winner = "O"
        }

        if( winner != "") {

            if (winner == "X"){
                setTurnText("Turn: X")
                addXScoreText()
                Toast.makeText(this,"X win the game", Toast.LENGTH_LONG).show()
                Log.i("Win", "X wins")
                clearBoard()

            }
            else {
                setTurnText("Turn: X")
                addOScoreText()
                Toast.makeText(this,"O  win the game", Toast.LENGTH_LONG).show()
                Log.i("Win", "O wins")
                clearBoard()
            }
        }
    }
}