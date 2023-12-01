package com.javierortizmi.currencyconverter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton

class MainActivity : AppCompatActivity() {
    private lateinit var indiaRB : RadioButton
    private lateinit var turkeyRB : RadioButton
    private lateinit var mexicoRB : RadioButton
    private lateinit var italyRB : RadioButton
    private lateinit var exchangeRateET : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        travel = Travel(0, 0.0)

        indiaRB = findViewById(R.id.india)
        turkeyRB = findViewById(R.id.turkey)
        mexicoRB = findViewById(R.id.mexico)
        italyRB = findViewById(R.id.italy)
        exchangeRateET = findViewById(R.id.exchangeRateNumber)

        updateView()
    }

    private fun updateView() {
        if (travel.getCountry() == 1) { // India
            indiaRB.isChecked = true
        }
        else if (travel.getCountry() == 2) { // Turkey
            turkeyRB.isChecked = true
        }
        else if (travel.getCountry() == 3) { // Mexico
            mexicoRB.isChecked = true
        }
        else if (travel.getCountry() == 4) { // Italy
            italyRB.isChecked = true
        }
        else {
            // Nothing
        }

        exchangeRateET.setText(travel.getExchangeRate().toString())
    }

    private fun updateTravelObject() {
        val country = if (indiaRB.isChecked)
            1
        else if (turkeyRB.isChecked)
            2
        else if (mexicoRB.isChecked)
            3
        else if (italyRB.isChecked)
            4
        else
            0
        travel.setCountry(country)

        val exchangeRateString : String = exchangeRateET.text.toString()
        try {
            val exchangeRate : Double = exchangeRateString.toDouble()
            travel.setExchangeRate(exchangeRate)
        } catch (nfe : NumberFormatException) {
            travel.setCountry(0)
        }
    }

    fun donePressed(v: View) {
        Log.w("MainActivity", "Inside donePressed id: $v")

        updateTravelObject()

        if (travel.getCountry() != 0) {
            // Go to DataActivity
            val intent = Intent(this, DataActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        lateinit var travel: Travel
    }
}