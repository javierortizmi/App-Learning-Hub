package com.javierortizmi.simongame

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var game : Game    // Create a Game object

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Nothing else needed here
    }

    override fun onResume() {
        super.onResume()
        game = Game(this)    // Init game so the bestLevel is updated every time the app
        // is onResume (even when we don't close the app completely)
        game.newLevel()             // Start at bestLevel + 1 and generate new Color
        Toast.makeText(this, game.getStringArray(), Toast.LENGTH_LONG).show() // Color Toast
    }

    override fun onPause() {
        super.onPause()
        game.setPreferences(this)   // bestLevel is updated each single time the app is onPause
        Log.w("Main Activity", "onPause value of bestLevel: ${game.getBestLevel()}")
    }

    fun addColor(v : View) {    // When ary of the color buttons is pressed

        val color = when (v.id) {   // 1 -> RED; 2 -> GREEN; 3 -> YELLOW; 4 -> BLUE
            R.id.redButton -> 1
            R.id.greenButton -> 2
            R.id.yellowButton -> 3
            else -> 4
        }
        Log.w("Activity Main", "This is the color: $color")

        when (game.userAddColor(color)) {   // With this function, we compare the color list
            // introduced by the user with the good one and returns
            // different values depending on the game state
            0 -> {  // User color list still smaller than correct color list
                // Continue gaming
            }
            1 -> {  // Lists are compared and they are equal, next level and Toast with new color
                Toast.makeText(this, game.getStringArray(), Toast.LENGTH_LONG).show()
            }
            else -> {   // Lists are compared and they aren't equal, app finishes
                finish()
            }
        }
    }

    fun resetLevel(v: View) {   // When reset button is pressed
        game.newGame()  // Set everything to 0 and start new level (level 1)
        Log.w("Main Activity", "Inside the button reset: ${v.id}\n" +
                "Now bestLevel is ${game.getBestLevel()}")
        Toast.makeText(this, game.getStringArray(), Toast.LENGTH_LONG).show() // Color Toast
    }
}