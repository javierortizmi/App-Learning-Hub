package com.javierortizmi.tictactoegame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView

@SuppressLint("ViewConstructor")
class ButtonGridAndTextView// V1

// create and add TextView at the bottom
// V3

// still V3

// still V3
// status.text = ttt.result()
// Set up this GridLayout
// rowCount = TicTacToe.SIDE

// Create the buttons
    (context: Context, width: Int, newSide: Int, listener: OnClickListener) :
    GridLayout(context) {
    private var side : Int = newSide
    private var buttons : Array<Array<GridButton>>
    private var status : TextView

    init {
        columnCount = side
        buttons = Array( TicTacToe.SIDE
        ) { i -> Array(TicTacToe.SIDE) { j -> GridButton(context, i, j) } }
        for( i in buttons.indices) {
            for( j in buttons[i].indices) {
                addView( buttons[i][j], width, width )

                // V1
                buttons[i][j].textSize = 72.0f
                buttons[i][j].setOnClickListener( listener )

            }
        }
        rowCount = side + 1
        val rowSpec : Spec = spec( side, 1 )
        val columnSpec : Spec = spec( 0, side )
        val lp = LayoutParams( rowSpec, columnSpec )
        status = TextView( context )
        status.layoutParams = lp
        status.width = width * side
        status.height = width
        status.gravity = Gravity.CENTER
        status.setBackgroundColor( Color.GREEN )
        status.textSize = 72.0f
        addView( status )
    }

    fun setStatusText( text: String? ) {
        status.text = text
    }

    fun setStatusBackgroundColor( color : Int ) {
        status.setBackgroundColor( color )
    }

    fun setButtonText( row : Int, column : Int,
                       text : String ) {
        buttons[row][column].text = text
    }

    fun resetButtons( ) {
        for( i in buttons.indices )
            for( j in buttons[i].indices )
                buttons[i][j].text = ""
    }

    fun enableButtons( enabled : Boolean ) {
        for( i in buttons.indices )
            for( j in buttons[i].indices )
                buttons[i][j].isEnabled = enabled
    }

    /*
    fun isButton( b : Button, row : Int,
                  column : Int ) : Boolean {
        return b == buttons[row][column]
    }
    */

}