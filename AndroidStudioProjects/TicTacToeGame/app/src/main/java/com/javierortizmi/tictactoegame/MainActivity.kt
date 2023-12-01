package com.javierortizmi.tictactoegame

import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var tttView : ButtonGridAndTextView
    private lateinit var ttt : TicTacToe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ttt = TicTacToe()

        buildGuiByCode()
    }

    private fun buildGuiByCode( ) {
        val width : Int  = Resources.getSystem().displayMetrics.widthPixels
        val w : Int = width / TicTacToe.SIDE

        // V1
        val handler = ButtonHandler()

        tttView = ButtonGridAndTextView( this, w, TicTacToe.SIDE, handler )
        tttView.setStatusText( ttt.result( ) )
        setContentView( tttView )
    }

    // V1
    fun update( row : Int, col : Int ) {
        Log.w( "MainActivity", "button clicked at row $row, column $col")
        // buttons[row][col].text = "X"

        // V2
        val play : Int = ttt.play( row, col )

        if( play == 1 )
            tttView.setButtonText( row, col, "X" )
        else if( play == 2 )
            tttView.setButtonText( row, col, "O" )
        if( ttt.isGameOver() ) {
            // disable the buttons
            tttView.enableButtons( false )
            tttView.setStatusText( ttt.result() )

            // offer to play again
            showNewGameDialog()
        }
    }

    private fun showNewGameDialog( ) {
        val alert : AlertDialog.Builder =
            AlertDialog.Builder( this )
        alert.setTitle( "This is fun" )
        alert.setMessage( "Play again?" )
        val playAgain = PlayDialog( )
        alert.setPositiveButton( "YES", playAgain )
        alert.setNegativeButton( "NO", playAgain )
        alert.show( )
    }

    // V1
    inner class ButtonHandler : View.OnClickListener {
        override fun onClick(v: View?) {
            val b : GridButton = v as GridButton
            update( b.getRow(), b.getCol())
        }
    }

    inner class PlayDialog : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface, id: Int) {
            if (id == -1) /* YES button */ {
                ttt.resetGame()
                // call methods of the View with tttView
                // in order to prepare the View for a new game
                tttView.enableButtons( true )
                tttView.resetButtons( )
                tttView.setStatusBackgroundColor( Color.GREEN )
                tttView.setStatusText( ttt.result( ) )
            } else if (id == -2) // NO button
                this@MainActivity.finish()
        }
    }

}