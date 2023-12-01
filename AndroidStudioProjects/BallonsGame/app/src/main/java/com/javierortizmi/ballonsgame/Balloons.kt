package com.javierortizmi.ballonsgame

import android.view.MotionEvent
import kotlin.math.pow
import kotlin.math.sqrt

class Balloons(private var balloons: ArrayList<Balloon>) {
    private var numberAttempts: Int = 0
    private val initialSize: Int = balloons.size

    fun getBalloons(): ArrayList<Balloon> {
        return balloons
    }

    fun updateBalloons(e: MotionEvent, statusBarHeight: Int) {
        // Log.w("MainActivity", "Event: ${e.x}, ${e.y - statusBarHeight}")
        numberAttempts++
        val iterator = balloons.iterator()
        while (iterator.hasNext()) {
            val balloon = iterator.next()
            val distance = sqrt((e.x - balloon.getX()).pow(2) +
                    ((e.y - statusBarHeight) - balloon.getY()).pow(2))
            if (distance <= balloon.getRadius())
                iterator.remove()
        }
    }

    fun isGameLost(): Boolean {
        return numberAttempts >= initialSize + 3
    }

    fun isGameWon(): Boolean {
        return balloons.size == 0
    }

}