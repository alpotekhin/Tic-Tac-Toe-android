package com.example.tic_tac_toe

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.collections.ArrayList



class SinglePlayerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)
    }
//  setting intents for buttons
    fun buttonIsClicked(view: View){
        val bPushed = view as Button
        var cellID = ""

//        Our grid:
//        A1 A2 A3
//        B1 B2 B3
//        C1 C2 C3

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

//    User plays as X and Phone as O
    var player_X = ArrayList<String>()
    var player_O = ArrayList<String>()


    private fun playGame(cellID: String, button: Button) {

//      if button "back" is pushed -> launches start screen
        if(cellID == "back") {
            val intent= Intent(this, StartActivity::class.java)
            startActivity(intent)
            Log.i("Activity", "Activity changed to Start Screen")
            return
        }

//      if button "reset" is pushed -> restarts game
        if(cellID == "reset") {
            restartGame()
            return
        }

        Log.i("Movement", "Player made a move")
        button.text = "X"

//      mark cell as occupied by X
        player_X.add(cellID)
//      X or O cannot be reassigned
        button.isEnabled = false
//      after player puts a char -> phone makes a turn
        phoneTurn("O")
//      checking winconditions
        checkWinner()
//      checking whether board is full
        if(isBoardFull()) {
//          showing "draw" message
            Toast.makeText(this,"Draw!", Toast.LENGTH_LONG).show()
            clearBoard()
        }
    }

    private fun phoneTurn(char: String) {
        val buttons = getButtons()
        var randIndex = (0..8).random()
        var turnNotMade = true

        while (turnNotMade){

            if(isBoardFull()) {
                return
            }

            if (buttons[randIndex].text == ""){
//                Timer().schedule(timerTask{  }, 300)
//              drawing phone turn on board
                buttons[randIndex].text = char
                buttons[randIndex].isEnabled=false
                turnNotMade = false
//              mark cell as occupied by O
                when(buttons[randIndex].id){
                    R.id.b1 -> player_O.add("A1")
                    R.id.b2 -> player_O.add("A2")
                    R.id.b3 -> player_O.add("A3")
                    R.id.b4 -> player_O.add("B1")
                    R.id.b5 -> player_O.add("B2")
                    R.id.b6 -> player_O.add("B3")
                    R.id.b7 -> player_O.add("C1")
                    R.id.b8 -> player_O.add("C2")
                    R.id.b9 -> player_O.add("C3")
                }
            }
            randIndex = (0..8).random()
        }
        Log.i("Movement", "Phone made a move")
    }

    @SuppressLint("SetTextI18n")
    private fun addXScoreText() {
//      increase player score
        val XScoreText: TextView = findViewById(R.id.XScoreText)
        XScoreText.text = (
                "Your score: " +
                        (XScoreText.text.split(" ")
                            .toTypedArray()[2].toInt() + 1).toString()
                )
        Log.i("Score", "Player gets a point")
    }

    @SuppressLint("SetTextI18n")
    private fun addOScoreText() {
//      increase phone score
        val OScoreText: TextView = findViewById(R.id.OScoreText)
        OScoreText.text = (
                "Your Phone score: " +
                        (OScoreText.text.split(" ")
                            .toTypedArray()[3].toInt() + 1).toString()
                )
        Log.i("Score", "Phone gets a point")
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
        XScoreText.text = "Your score: 0"
        OScoreText.text = "Your Phone score: 0"
        Log.i("Game", "Game is restarted")
    }


    private fun checkRow(row: ArrayList<String>): Boolean {
//      checks all row winconditions
        if(row.contains("A1") and row.contains("A2") and row.contains("A3")
            || row.contains("B1") and row.contains("B2") and row.contains("B3")
            || row.contains("C1") and row.contains("C2") and row.contains("C3"))
        {
            return true
        }
        return false

    }

    private fun checkColumn(column: ArrayList<String>): Boolean {
//      checks all column winconditions
        if(column.contains("A1") and column.contains("B1") and column.contains("C1")
            || column.contains("A2") and column.contains("B2") and column.contains("C2")
            || column.contains("A3") and column.contains("B3") and column.contains("C3"))
        {
            return true
        }
        return false
    }

    private fun checkDiag(diag: ArrayList<String>): Boolean {
//      checks all diagonal winconditions
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

            if (winner == "X") {
                addXScoreText()
                Toast.makeText(this,"You win the game", Toast.LENGTH_LONG).show()
                clearBoard()
                Log.i("Win", "Player won")
                phoneTurn("O")
            }
            else {
                Log.i("Win", "Phone won")
                addOScoreText()
                Toast.makeText(this,"Phone wins the game", Toast.LENGTH_LONG).show()
                clearBoard()
            }
        }
    }
}