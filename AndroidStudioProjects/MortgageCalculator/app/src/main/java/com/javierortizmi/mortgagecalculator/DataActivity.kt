package com.javierortizmi.mortgagecalculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

class DataActivity : AppCompatActivity() {
    private lateinit var amountET : EditText
    private lateinit var rateET : EditText
    private lateinit var button10 : RadioButton
    private lateinit var button15 : RadioButton
    private lateinit var button30 : RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_data)
        Log.w( "MainActivity", "inside DataActivity:onCreate" )

        amountET = findViewById( R.id.data_amount )
        rateET = findViewById( R.id.data_rate )
        button10 = findViewById( R.id.ten )
        button15 = findViewById( R.id.fifteen )
        button30 = findViewById( R.id.thirty )

        updateView( )

    }

    override fun onStart(  ) {
        super.onStart()
        Log.w( "MainActivity", "inside DataActivity:onStart" )
    }

    override fun onRestart( ) {
        super.onRestart()
        Log.w( "MainActivity", "inside DataActivity:onReStart" )
    }

    override fun onPause(  ) {
        super.onPause()
        Log.w( "MainActivity", "inside DataActivity:onPause" )
    }

    override fun onStop(  ) {
        super.onStop()
        Log.w( "MainActivity", "inside DataActivity:onStop" )
    }

    override fun onResume(  ) {
        super.onResume()
        Log.w( "MainActivity", "inside DataActivity:onResume" )
    }

    override fun onDestroy( ) {
        super.onDestroy()
        Log.w( "MainActivity", "inside DataActivity:onDestroy" )
    }

    private fun updateView( ) {
        amountET.setText( MainActivity.mortgage.getAmount().toString() )
        rateET.setText( MainActivity.mortgage.getInterestRate().toString() )

        if( MainActivity.mortgage.getYears() ==  10 )
            button10.isChecked = true
        else if( MainActivity.mortgage.getYears() == 15 )
            button15.isChecked = true
        else
            button30.isChecked = true
    }

    private fun updateMortgageObject( ) {
        var years = 30
        if( button10.isChecked )
            years = 10
        else if ( button15.isChecked )
            years = 15
        MainActivity.mortgage.setYears( years )
        Log.w( "MainActivity", "amountET.text is $amountET.text" )
        val amountString : String = amountET.text.toString()
        val rateString : String = rateET.text.toString()
        try {
            val amount : Float = amountString.toFloat()
            MainActivity.mortgage.setAmount( amount )
            val rate : Float = rateString.toFloat()
            MainActivity.mortgage.setInterestRate( rate )
        } catch( nfe : NumberFormatException ) {
            // some code here
        }

        MainActivity.mortgage.setPreferences( this )
    }

    fun goBack( v : View) {
        Log.w("Main Activity", "${v.id}")

        updateMortgageObject( )

        //finish( )

        // V4 (A)
        // overridePendingTransition(R.anim.fade_in_scale,0)

        // V4 (B)
        finishAfterTransition()
    }
}