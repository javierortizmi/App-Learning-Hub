package com.javierortizmi.duckhuntinggame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.util.Log
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

@SuppressLint("ViewConstructor")
class GameView(context: Context, width: Int, height: Int) : View(context) {
    private var paint : Paint = Paint( )

    private var duck : Bitmap
    private var ducks : Array<Bitmap>
    private var duckFrame : Int  = 0
    private var duckRect : Rect

    private var game : Game

    init {
        paint.color = Color.BLACK
        paint.isAntiAlias = true
        paint.strokeWidth = 20.0f
        duck = BitmapFactory.decodeResource( resources, TARGET )
        ducks = Array ( TARGETS.size) { i -> BitmapFactory.decodeResource(resources, TARGETS[i]) }
        val scale : Float = width.toFloat() / (duck.width * 5 )
        duckRect = Rect( width - width / 5, 0, width, (duck.height * scale).toInt() )
        game = Game( duckRect, 5, .03f, .2f )
        game.setDuckSpeed( width * .00003f )
        game.setBulletSpeed( width * .0003f )
        game.setHuntingRect( Rect( 0,0, width, height ) )
        game.setCannon( Point( 0, height ), width / 30, width / 15, width / 50 )
        game.setDeltaTime( DELTA_TIME )
    }

    fun getGame( ) : Game {
        return game
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Log.w( "MainActivity", "Inside onDraw" )

        // draw the cannon
        val cx : Float = game.getCannonCenter().x.toFloat( )
        val cy : Float = game.getCannonCenter().y.toFloat( )
        canvas.drawCircle( cx, cy, game.getCannonRadius().toFloat(), paint )
        val bx = game.getBarrelLength() * cos( game.getCannonAngle().toDouble()).toFloat()
        val by = game.getBarrelLength() * sin( game.getCannonAngle().toDouble()).toFloat()
        canvas.drawLine( cx, cy, cx + bx, cy - by, paint)

        // canvas.drawBitmap( duck, null, game.getDuckRect(), paint )
        canvas.drawBitmap( ducks[duckFrame], null, game.getDuckRect(), paint )
        duckFrame = ( duckFrame + 1 ) % TARGETS.size
        // draw the bullet
        if( !game.bulletOffScreen() ) {
            Log.w("MainActivity", "drawing bullet")
            val x = game.getBulletCenter().x
            val y = game.getBulletCenter().y
            val radius = game.getBulletRadius()
            Log.w( "MainActivity", "x, y, radius = $x; $y; $radius")
            canvas.drawCircle(
                game.getBulletCenter().x.toFloat(),
                game.getBulletCenter().y.toFloat(), game.getBulletRadius().toFloat(), paint
            )
        }
    }

    companion object {
        var TARGET : Int = R.drawable.duck
        val TARGETS : IntArray = intArrayOf( R.drawable.anim_duck0, R.drawable.anim_duck1,
            R.drawable.anim_duck2, R.drawable.anim_duck1)
        const val DELTA_TIME : Int  = 200
    }
}