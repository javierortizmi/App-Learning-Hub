package com.javierortizmi.basiccalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    // 0 -> Nada, 1 -> Suma, 2 -> Subtract, 3 -> Multiplication, 4 -> Division
    private var operationNum: Int = 0
    private var number1: Double = 0.0
    private lateinit var tvNum1: TextView
    private lateinit var tvNum2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvNum1 = findViewById(R.id.tv_num1)
        tvNum2 = findViewById(R.id.tv_num2)
        val btnDelete: Button = findViewById(R.id.btnC)
        val btnIgual: Button = findViewById(R.id.btnEqual)

        btnIgual.setOnClickListener{
            val number2: Double = tvNum2.text.toString().toDouble()
            var answer = 0.0

            when(operationNum) {
                1 -> answer = number1 + number2
                2 -> answer = number1 - number2
                3 -> answer = number1 * number2
                4 -> answer = number1 / number2
            }

            tvNum2.text = answer.toString()
            tvNum1.text = ""
        }

        btnDelete.setOnClickListener{
            tvNum1.text = ""
            tvNum2.text = ""
            number1 = 0.0
            operationNum = 0
        }
    }


    fun pressDigit(view: View){
        // val tv_num2: TextView = findViewById(R.id.tv_num2)
        val num2: String = tvNum2.text.toString()

        when(view.id){
            R.id.btn0 -> tvNum2.text = getString(R.string.zero, num2)
            R.id.btn1 -> tvNum2.text = getString(R.string.one, num2)
            R.id.btn2 -> tvNum2.text = getString(R.string.two, num2)
            R.id.btn3 -> tvNum2.text = getString(R.string.three, num2)
            R.id.btn4 -> tvNum2.text = getString(R.string.four, num2)
            R.id.btn5 -> tvNum2.text = getString(R.string.five, num2)
            R.id.btn6 -> tvNum2.text = getString(R.string.six, num2)
            R.id.btn7 -> tvNum2.text = getString(R.string.seven, num2)
            R.id.btn8 -> tvNum2.text = getString(R.string.eight, num2)
            R.id.btn9 -> tvNum2.text = getString(R.string.nine, num2)
            R.id.btnComma -> tvNum2.text = getString(R.string.dot, num2)
        }
    }

    @SuppressLint("SetTextI18n")
    fun clickOperation(view: View){
        number1 = tvNum2.text.toString().toDouble()
        val num2Text: String = tvNum2.text.toString()
        tvNum2.text = ""

        when(view.id){
            R.id.btnPlus -> {
                tvNum1.text = "$num2Text+"
                operationNum = 1
            }
            R.id.bynMinus -> {
                tvNum1.text = "$num2Text-"
                operationNum = 2
            }
            R.id.btnMultiply -> {
                tvNum1.text = "$num2Text*"
                operationNum = 3
            }
            R.id.btnDivide -> {
                tvNum1.text = "$num2Text/"
                operationNum = 4
            }
        }
    }
}