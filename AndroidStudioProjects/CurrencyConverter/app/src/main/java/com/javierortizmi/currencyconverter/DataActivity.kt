package com.javierortizmi.currencyconverter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DataActivity : AppCompatActivity() {
    private lateinit var goodMorningTV : TextView
    private lateinit var pleaseTV : TextView
    private lateinit var enterAmountET : EditText
    private lateinit var amountNumberTV : TextView
    private lateinit var needVisaTV : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        goodMorningTV = findViewById(R.id.goodMorningTranslated)
        pleaseTV = findViewById(R.id.pleaseTranslated)
        enterAmountET = findViewById(R.id.enterAmount)
        amountNumberTV = findViewById(R.id.amountNumber)
        needVisaTV = findViewById(R.id.needVisaAnswer)

        updateView()
    }

    private fun updateView() {
        if (MainActivity.travel.getCountry() == 1) {        // India
            goodMorningTV.text = getString(R.string.goodMorningIndia)
            pleaseTV.text = getString(R.string.pleaseIndia)
            needVisaTV.text = getString(R.string.yes)
        }
        else if (MainActivity.travel.getCountry() == 2) {   // Turkey
            goodMorningTV.text = getString(R.string.goodMorningTurkey)
            pleaseTV.text = getString(R.string.pleaseTurkey)
            needVisaTV.text = getString(R.string.yes)
        }
        else if (MainActivity.travel.getCountry() == 3) {   // Mexico
            goodMorningTV.text = getString(R.string.goodMorningMexico)
            pleaseTV.text = getString(R.string.pleaseMexico)
            needVisaTV.text = getString(R.string.no)
        }
        else {                                              // Italy
            goodMorningTV.text = getString(R.string.goodMorningItaly)
            pleaseTV.text = getString(R.string.pleaseItaly)
            needVisaTV.text = getString(R.string.yesButNo)
        }
    }

    fun updateAmount(v: View) {
        Log.w("Main Activity", "Inside updateAmount: ${v.id}")
        try {
            amountNumberTV.text = MainActivity.travel.calculateMoneyExchange(enterAmountET.text.toString().toDouble())
        } catch (e : Exception) {
            // Do nothing
        }

    }

    fun goBack(v: View) {
        Log.w("Main Activity", "Inside goBack id: ${v.id}")
        finish()
    }
}