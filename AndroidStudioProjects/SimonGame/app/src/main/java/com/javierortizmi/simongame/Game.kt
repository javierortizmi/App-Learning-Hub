package com.javierortizmi.simongame

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

const val DEFAULT_LEVEL: Int = 0

class Game(context: Context) {
    private var colorArray = ArrayList<Int>()    //  1 -> RED; 2 -> GREEN; 3 -> YELLOW; 4 -> BLUE
    private var userColorArray = ArrayList<Int>()   // Colors introduced by the user
    private var level = 0
    private var bestLevel = 0

    init {
        val pref : SharedPreferences =
            context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
        setBestLevel(pref.getInt(PREFERENCE_BEST_LEVEL, DEFAULT_LEVEL))  // Best level is set with stored value
        setLevel(getBestLevel())                             // Set level at best level
    }

    // GETTERS AND SETTERS
    private fun getLevel(): Int {
        return level
    }

    private fun setLevel(level: Int) {
        this.level = level
    }

    fun getBestLevel(): Int {
        return bestLevel
    }

    private fun setBestLevel(bestLevel: Int) {
        this.bestLevel = bestLevel
    }

    private fun getColorArray() : ArrayList<Int> {
        return colorArray
    }

    private fun getColorList() {    // Returns all the colors of the list (in numbers)
        for (i in getColorArray())
            Log.w("Main Activity", "$i")
    }

    fun userAddColor(color : Int): Int {    // Called when user clicks on any color button
        Log.w("Activity Main", "Inside AddColor")

        userColorArray.add(color)           // Clicked color adds to the user color list

        if (userColorArray.size == colorArray.size) {   // When they are same size, compare lists
            return if (compareColorsResult()) {    // Does the comparison have to be in each single
                // button pressed or only after sequence?
                Log.w("Main Activity", "Right combination")
                if (getLevel() > getBestLevel()){  // Check that the current level is greater
                    setBestLevel(getLevel())       // tha best level -> New best level
                    Log.w("Main Activity", "Best Level set to: ${getBestLevel()}")
                }
                Log.w("Main Activity", "Best Level is: ${getBestLevel()}")
                newLevel()      // New level
                1
            } else {
                // Terminate app
                Log.w("Main Activity", "Wrong combination")
                2
            }
        } else
            return 0
    }

    private fun generateColor() {
        while (colorArray.size < level) {   // So if the game initializes at level 4,
            // 4 colors are generated
            val color = (1..4).random()     // Call the random function between 1 & 4
            colorArray.add(color)                 // add color to the stack
            Log.w("Activity Main", "This is the generated color: $color")
        }
    }

    private fun compareColorsResult() : Boolean{    // Returns true if both lists are equal
        return colorArray==userColorArray
    }

    fun newLevel() {
        setLevel(getLevel() + 1)    // Add 1 level
        Log.w("Main Activity", "Now you are in level: ${getLevel()}")
        userColorArray.clear()      // Empties the stack of the user color list
        generateColor()             // Generate new color and add it to the color list
        getColorList()              // Just prints in Logcat
    }

    fun newGame() {                 // Initialize everything to 0
        setBestLevel(0)
        setLevel(0)
        colorArray.clear()
        newLevel()                  // Start at level 1
    }

    fun getStringArray(): String {  // Returns a string with the current list of colors to guess
        var colorPattern = ""
        for (i in colorArray) {
            colorPattern += when (i) {
                1 -> "Red "
                2 -> "Green "
                3 -> "Yellow "
                else -> "Blue "
            }
        }
        return colorPattern
    }

    fun setPreferences(context: Context) {  // Store persistent data
        val pref : SharedPreferences =
            context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)

        val editor : SharedPreferences.Editor = pref.edit()
        editor.putInt(PREFERENCE_BEST_LEVEL, bestLevel)
        editor.apply()
    }

    companion object {
        private const val PREFERENCE_BEST_LEVEL : String = "bestLevel"
    }
}