package com.javierortizmi.mortgagecalculator

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var amountTV: TextView
    private lateinit var yearsTV: TextView
    private lateinit var rateTV: TextView
    private lateinit var monthlyTV: TextView
    private lateinit var totalTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // setContentView(R.layout.activity_data)

        Log.w("MainActivity", "inside MainActivity:onCreate")

        // mortgage = Mortgage(30, .035f, 100000.0f)
        mortgage = Mortgage( this )

        amountTV = findViewById(R.id.amount)
        yearsTV = findViewById(R.id.years)
        rateTV = findViewById(R.id.rate)
        monthlyTV = findViewById(R.id.payment)
        totalTV = findViewById(R.id.total)
    }

    override fun onStart() {
        super.onStart()
        Log.w("MainActivity", "inside MainActivity:onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.w("MainActivity", "inside MainActivity:onReStart")
    }

    override fun onPause() {
        super.onPause()
        Log.w("MainActivity", "inside MainActivity:onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.w("MainActivity", "inside MainActivity:onStop")
    }

    override fun onResume() {
        super.onResume()
        Log.w("MainActivity", "inside MainActivity:onResume")
        updateView()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w("MainActivity", "inside MainActivity:onDestroy")
    }

    fun modifyData(v: View) {
        Log.w("Main Activity", "Inside modifyData: $v")
        // go to DataActivity
        val intent = Intent(this, DataActivity::class.java)

        //startActivity(intent)

        // V4 (A)
        //overridePendingTransition(R.anim.slide_left, 0)

        // V4 (B)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    private fun updateView( ) {
        amountTV.text = mortgage.getAmount().toString()
        rateTV.text = mortgage.getInterestRate().toString()
        yearsTV.text = mortgage.getYears().toString()
        monthlyTV.text = mortgage.formattedMonthlyPayment()
        totalTV.text = mortgage.formattedTotalPayments()
    }

    companion object {
        lateinit var mortgage: Mortgage
    }
}