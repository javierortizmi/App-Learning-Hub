package com.javierortizmi.ballonsgame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

@SuppressLint("ViewConstructor")
class GameView(context: Context, private var balloons: Balloons): View(context) {
    private var paint = Paint()

    init {
        paint.color = Color.BLUE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (balloon in balloons.getBalloons()) {
            canvas.drawCircle(balloon.getX().toFloat(), (balloon.getY()).toFloat(), balloon.getRadius().toFloat(), paint)
        }
    }
}