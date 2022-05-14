package com.example.tic_tac_toe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StartActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    fun buttonIsClicked(view: View){
        val bPushed = view as Button
        when(bPushed.id){
            R.id.singGameButton -> runSingleGame()
            R.id.multiGameButton -> runMultiPlayerGame()

        }
    }

    private fun runSingleGame(){
        val intent= Intent(this, SinglePlayerActivity::class.java)
        startActivity(intent)
    }

    private fun runMultiPlayerGame(){
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}