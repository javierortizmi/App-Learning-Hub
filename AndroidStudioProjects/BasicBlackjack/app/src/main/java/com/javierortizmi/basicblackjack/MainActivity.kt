package com.javierortizmi.basicblackjack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var blackJackGame : Blackjack
    private var dealNO : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        blackJackGame = Blackjack(0)
    }

    fun deal(v : View) {
        Log.w(" MainActivity", "Some info of the DEAL Button $v")

        val gameStatus : TextView = findViewById(R.id.gameStatus)
        val currentTotal: TextView = findViewById(R.id.currentTotal)

        if (blackJackGame.getCurrentTotal() < 17) {
            dealNO++
            lateinit var cardTurn : Button
            if (dealNO == 1) {
                cardTurn = findViewById(R.id.card1)
                cardTurn.text = blackJackGame.updateCards(dealNO)
                cardTurn = findViewById(R.id.card2)
                cardTurn.text = blackJackGame.updateCards(dealNO)
            }
            if (dealNO == 2) {
                cardTurn = findViewById(R.id.card3)
                cardTurn.text = blackJackGame.updateCards(dealNO)
            }
            if (dealNO == 3) {
                cardTurn = findViewById(R.id.card4)
                cardTurn.text = blackJackGame.updateCards(dealNO)
            }
            currentTotal.text = blackJackGame.getCurrentTotal().toString()
        }

        if (blackJackGame.getCurrentTotal() in 17..21) {
            gameStatus.text = getString(R.string.won)
        }

        if (blackJackGame.getCurrentTotal() > 21) {
            gameStatus.text = getString(R.string.lost)
        }

    }
}