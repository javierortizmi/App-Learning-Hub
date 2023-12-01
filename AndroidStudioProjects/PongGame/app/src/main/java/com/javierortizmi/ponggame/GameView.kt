package com.javierortizmi.ponggame

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.view.View

const val DEFAULT_LEVEL: Int = 0

@SuppressLint("ViewConstructor")
class GameView(context: Context, private var width: Int, private var height: Int): View(context) {
    private var paint: Paint = Paint()

    private var pong: Pong = Pong(DELTA_TIME)

    private var level: Int = 0
    private var bestLevel: Int = 0
    private var gameLost: Boolean = false
    private var isBestLevel: Boolean = false

    init {
        paint.textSize = (width/15).toFloat()

        pong.setBall(Point(width/2, 0+(width/30)), 1, width/30)
        pong.setPaddle(Point(width/2, height-(height/7)), width/8, height/300)

        val pref : SharedPreferences = context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
        setBestLevel(pref.getInt(PREFERENCE_BEST_LEVEL, DEFAULT_LEVEL))  // Best level is set with stored value

    }

    fun getPong(): Pong {
        return pong
    }

    fun getWidthValue(): Int {
        return width
    }
    fun getHeightValue(): Int {
        return height
    }

    fun newLevel() {
        level += 1
    }

    fun getLevel(): Int {
        return level
    }

    fun getBestLevel(): Int {
        return bestLevel
    }
    private fun setBestLevel(level: Int) {
        this.bestLevel = level
    }
    private fun bestLevelText(): String {
        return if (isBestLevel)
            "This is a New Best Score! :)"
        else
            "Sorry, no New Best Score :("
    }

    fun setGameLost(gameLost: Boolean) {
        this.gameLost = gameLost
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(pong.getBall().getBallCentre().x.toFloat(), pong.getBall().getBallCentre().y.toFloat(), pong.getBall().getBallRadius().toFloat(), paint)
        // canvas.drawLine((width/2-100).toFloat(), (height-200).toFloat(), (width/2+100).toFloat(), (height-200).toFloat(), paint)
        canvas.drawRect(
            (pong.getPaddle().getPaddlePosition().x-pong.getPaddle().getPaddleWidth()).toFloat(),
            (pong.getPaddle().getPaddlePosition().y-pong.getPaddle().getPaddleHeight()).toFloat(),
            (pong.getPaddle().getPaddlePosition().x+pong.getPaddle().getPaddleWidth()).toFloat(),
            (pong.getPaddle().getPaddlePosition().y+pong.getPaddle().getPaddleHeight()).toFloat(),
            paint)

        if (gameLost) {
            canvas.drawText(("Score: $level"), (width/2 - (("Score: $level").length * paint.textSize / 5)), (height/2).toFloat(), paint)
            canvas.drawText(bestLevelText(), (width/2 - (bestLevelText().length * paint.textSize / 5)), (height/2 + height/15).toFloat(), paint)
        }

    }

    companion object {
        const val DELTA_TIME : Int = 1
        private const val PREFERENCE_BEST_LEVEL : String = "bestLevel"
    }

    fun setPreferences(context: Context) {  // Store persistent data

        if (level > bestLevel) {
            bestLevel = level
            isBestLevel = true
        }

        val pref : SharedPreferences =
            context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)

        val editor : SharedPreferences.Editor = pref.edit()
        editor.putInt(PREFERENCE_BEST_LEVEL, bestLevel)
        editor.apply()
    }
}