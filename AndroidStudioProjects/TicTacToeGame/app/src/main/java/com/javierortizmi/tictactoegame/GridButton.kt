package com.javierortizmi.tictactoegame

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.widget.AppCompatButton

@SuppressLint("ViewConstructor")
class GridButton(context: Context, row: Int, col: Int) : AppCompatButton(context) {

    private var row : Int = 0
    private var col : Int = 0

    init {
        setRow( row )
        setCol( col )
    }

    private fun setRow(row : Int ) {
        if( row >= 0 && row < TicTacToe.SIDE )
            this.row = row
    }

    private fun setCol(col : Int ) {
        if( col >= 0 && col < TicTacToe.SIDE )
            this.col = col
    }

    fun getRow( ) : Int {
        return row
    }

    fun getCol( ) : Int {
        return col
    }
}