package com.javierortizmi.helloandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {   // MainActivity class extends AppCompatActivity class
    override fun onCreate(savedInstanceState: Bundle?) {  // onCreate method created automatically
        super.onCreate(savedInstanceState)      // Calling the super method
        setContentView(R.layout.activity_main)  // Sets the View for this Activity
        // R stands for resources and represents the res folder
        // We will approach the XML file with the GUI
        Log.w("MainActivity", "Inside onCreate")
        Log.w( "MainActivity", "resource: " +
                R.layout.activity_main)

        var sum = 0
        for (i in 1..3) {
            sum += 1
        }

        // DATA TYPES
        var price: Float   // var variableName : dataType
        var name = "John"
        val piF = 3.14159f  // val for immutable variables
        val daysInWeek = 7
        // const val DAYS : Int = 365  -> const keyword for constants
        var stars : Int = "234762".toInt()
        var grades = IntArray(30)   // For a size 30 array. Also, CharArray, FloatArray, ...
        var grades2 : IntArray = intArrayOf(89,87,90)   // Hard coded contents
        var words : Array<String> = Array(10) { "All Hi" } // Use Array constructor
        var numbers : Array<Array<Int>> = Array(4) { i ->
            Array(
                6
            ) { j -> i + j }
        }   // 2-dim Arrays XD
    }

    // FUNCTIONS
    fun add(a : Int, b : Int) : Int {   // Arguments in parenthesis and the return type
        return a+b
    }

    // NULL POINTER EXCEPTIONS
    private fun foo1(s : String) : Int {
        // s "cannot" be NULL when calling foo1
        return 0
    }
    // var s0 : String = null -> Does NOT Compile
    private var s1 : String = "Hello"
    var result1 : Int = foo1(s1) // Compiles
    private var s2 : String? = null // Compiles
    // var result2 : Int = foo1(s2) -> Does NOT Compile
    var result2 : Int = foo1(s2!!) // Compiles, might generate a NPE
    fun foo2(s : String?) : Int {
        // ? -> s can be null when calling foo2
        // ..BUT we must handle situations when
        // s is null
        return if(s != null) {
            0
        } else 1
    }

    // UNIT FUNCTIONS
    fun foo3(a : Int,b : Int) {  // Unit is the equivalent of void in Java
        // Some code here
    }

    // CLASS
    class A {
        private var grade : Int = 0
        fun setGrade(g : Int) {
            grade=g
        }
        fun addToGrade(extra : Int) {
            this.grade += extra // Can use this keyword
        }
    }
    private var a : A = A() // No new keyword
    val b = a.setGrade(85)
    val c = a.addToGrade(1)
}