package com.javierortizmi.tipcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.javierortizmi.tipcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var billET : EditText
    private lateinit var tipET : EditText
    private lateinit var tipTV : TextView
    private lateinit var totalTV : TextView

    private lateinit var tipCalc : TipCalculator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView( binding.root )
        // setContentView(R.layout.activity_main)

        billET = findViewById( R.id.amount_bill )
        tipET = findViewById( R.id.amount_tip_percent )
        tipTV = findViewById( R.id.amount_tip )
        totalTV = findViewById( R.id.amount_total )

        tipCalc = TipCalculator( 0.0, 0.0 )

        // #2 in event handling
        val tch = TextChangeHandler()
        // #3 in event handling: register tch on the two EditTexts
        tipET.addTextChangedListener( tch )
        billET.addTextChangedListener( tch )

        val tipTV2 : TextView = binding.amountTip
        Log.w( "MainActivity", "tipTV is $tipTV")
        Log.w( "MainActivity", "tipTV2 is $tipTV2")

    }

    fun calculate( ) {
        val billString : String = billET.text.toString()
        val tipString : String = tipET.text.toString()

        try {
            val bill : Double = billString.toDouble()
            val tip : Double = tipString.toDouble() / 100
            tipCalc.setBill( bill )
            tipCalc.setTip( tip )

            tipTV.text = tipCalc.formattedTipAmount()
            totalTV.text = tipCalc.formattedTotalAmount()

        } catch( e : Exception ) {
            Log.w( "MainActivity", "inside catch, error" )
            // handle incorrect input here
        }

    }

    // #1 in event handling
    inner class TextChangeHandler : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // do nothing
        }

        override fun afterTextChanged(s: Editable?) {
            Log.w( "MainActivity" , "Inside afterTextChanged" )
            this@MainActivity.calculate( )
        }

    }
}