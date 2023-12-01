package com.javierortizmi.tipcalculator

import android.util.Log
import java.text.NumberFormat

// const val DAYS_IN_WEEK : Int  = 7

class TipCalculator(newBill: Double, tip: Double) {
    private var bill : Double = 0.0
    private var tip : Double = 0.0


    init {
        Log.w( "CMSC", "Inside constructor" )
        setBill( newBill )
        setTip( tip )
    }

    fun setTip( newTip : Double ) {
        Log.w( "CMSC", "Inside setTip" )
        if ( newTip >= 0.0 )
            tip = newTip
    }

    fun setBill( bill : Double ) : TipCalculator {
        Log.w( "CMSC", "Inside setBill" )
        if( bill >= 0 )
            this.bill = bill
        return this
    }

    private fun tipAmount( ) : Double {
        return tip * bill
    }

    private fun totalAmount( ) : Double {
        return (1 + tip ) * bill
    }

    fun formattedTipAmount( ) : String {
        val currency : NumberFormat = NumberFormat.getCurrencyInstance()
        return currency.format( tipAmount( ) )
    }

    fun formattedTotalAmount( ) : String {
        val currency : NumberFormat = NumberFormat.getCurrencyInstance()
        return currency.format( totalAmount( ) )
    }

    /*companion object {
        const val DAYS_IN_YEAR = 365

        fun sayHI( ) : String {
            return "HI"
        }

        fun test( ) : Int {
            return 1
        }
    }*/
}