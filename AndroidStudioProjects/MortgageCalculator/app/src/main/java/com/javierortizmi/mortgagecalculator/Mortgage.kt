package com.javierortizmi.mortgagecalculator

import android.content.Context
import android.content.SharedPreferences
import java.text.NumberFormat
import kotlin.math.pow

// global constant
// const val TAX_RATE : Double = .05

class Mortgage( context : Context) {
    private var years : Int = 30
    private var interestRate : Float = 0.05f
    private var amount : Float = 0.0f

    // local constant
    // val DEFAULT_TIP : Double = .18

    init {
        val pref : SharedPreferences = context.getSharedPreferences( context.packageName + "_preferences",
            Context.MODE_PRIVATE )
        setYears( pref.getInt( PREFERENCE_YEARS, 30 ))
        setAmount( pref.getFloat( PREFERENCE_AMOUNT, 100000.0f))
        setInterestRate( pref.getFloat( PREFERENCE_RATE, .035f ))
    }

    /* constructor( newYears : Int, newInterestRate : Float, newAmount : Float ) {
        setYears( newYears )
        setInterestRate( newInterestRate )
        setAmount( newAmount )
    } */

    fun getYears( ) : Int {
        return years
    }

    fun getInterestRate( ) : Float {
        return interestRate
    }

    fun getAmount( ) : Float {
        return amount
    }

    // void method, using a parameter name different from the instance variable name
    fun setYears( newYears : Int ) {
        if( newYears >= 0 )
            years = newYears
    }

    fun setInterestRate( newInterestRate : Float ) {
        if( newInterestRate >= 0 )
            interestRate = newInterestRate
    }

    fun setAmount( newAmount : Float ) {
        if( newAmount >= 0 )
            amount = newAmount
    }

    private fun monthlyPayment( ) : Float {
        val monthlyInterestRate = interestRate / 12
        val a : Float = 1 / ( 1 + monthlyInterestRate )
        val b : Float = ( ( a.toDouble().pow((years * 12).toDouble()) - 1 ) / ( a - 1  ) ).toFloat()
        return amount / ( a * b )
    }

    fun formattedMonthlyPayment( ) : String {
        val nf : NumberFormat = NumberFormat.getCurrencyInstance()
        return nf.format( monthlyPayment() )
    }

    private fun totalPayments( ) : Float {
        return years * 12 * monthlyPayment()
    }

    fun formattedTotalPayments( ) : String {
        val nf : NumberFormat = NumberFormat.getCurrencyInstance()
        return nf.format( totalPayments() )
    }

    fun setPreferences( context : Context ) {
        val pref : SharedPreferences = context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)

        val editor : SharedPreferences.Editor = pref.edit()
        editor.putFloat(PREFERENCE_AMOUNT, amount)
        editor.putFloat(PREFERENCE_RATE, interestRate)
        editor.putInt(PREFERENCE_YEARS, years)
        editor.apply()
    }

    companion object {
        private const val PREFERENCE_AMOUNT : String = "amount"
        private const val PREFERENCE_YEARS : String = "years"
        private const val PREFERENCE_RATE : String = "rate"
    }
}