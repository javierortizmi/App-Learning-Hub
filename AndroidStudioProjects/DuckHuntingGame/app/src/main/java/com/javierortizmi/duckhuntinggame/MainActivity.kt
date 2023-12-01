package com.javierortizmi.duckhuntinggame

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Resources
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import java.util.Timer
import kotlin.math.atan2

class MainActivity : Activity() {
    private lateinit var gameView : GameView    // What we are gonna call in setContentView
    private lateinit var game : Game            // Model for the game

    private lateinit var pool : SoundPool       // For the sound
    private var fireSoundId : Int = 0           // ID for the sound
    private lateinit var detector : GestureDetector // For gestures

    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        // Retrieve width and height of the screen
        val width : Int = Resources.getSystem().displayMetrics.widthPixels
        val height : Int = Resources.getSystem().displayMetrics.heightPixels

        // retrieve status bar height
        val statusBarId : Int = resources.getIdentifier( "status_bar_height", "dimen", "android" )
        val statusBarHeight : Int  = resources.getDimensionPixelSize( statusBarId )

        // Create a GameView object with the dimensions of the screen
        gameView = GameView( this, width, height - statusBarHeight )
        game = gameView.getGame()
        setContentView( gameView )  // Set the view of the app

        // set up event handling
        val handler = TouchHandler()    // TouchHandler class
        detector = GestureDetector( this, handler ) // Call the constructor with the handler
        detector.setOnDoubleTapListener( handler )      // Listens for double tap


        // set schedule
        val gameTimer = Timer( )
        val gameTimerTask = GameTimerTask( this )
        gameTimer.schedule( gameTimerTask, 0L, GameView.DELTA_TIME.toLong() )   // Sets DELTA
        // TIME as the time step

        // For the sound
        val poolBuilder : SoundPool.Builder = SoundPool.Builder( )
        // added poolBuilder.setMaxStreams( 2 )
        pool = poolBuilder.build()
        fireSoundId = pool.load( this, R.raw.cannon_fire, 1 )
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if( event != null )
            detector.onTouchEvent( event )
        return super.onTouchEvent(event)
    }

    // Update the model
    fun updateModel( ) {
        if( game.bulletOffScreen() )
            game.loadBullet()
        else if( game.isBulletFired())
            game.moveBullet()
        game.moveDuck()
        if( game.duckOffScreen() )
            game.startDuckFromRightTopHalf()
        else if( game.duckHit() ) {
            game.setDuckShot( true )
            game.loadBullet()
        }
    }

    // Update the view
    fun updateView( ) {
        gameView.postInvalidate()
    }

    // Handle different types of gestures/touches to the screen
    inner class TouchHandler : GestureDetector.SimpleOnGestureListener ( ) {
        // Single Tap
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            updateCannon(e)
            return super.onSingleTapConfirmed(e)
        }

        // Scroll
        override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            updateCannon(e2)
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        // Double Tap
        override fun onDoubleTapEvent(e: MotionEvent): Boolean {
            if( ! game.isBulletFired() ) {
                Log.w("MainActivity", "Firing bullet")
                pool.play( fireSoundId, 1.0f, 1.0f, 0, 0, 1.0f )
                game.fireBullet()
            }
            return super.onDoubleTapEvent(e)
        }

        // Update cannon
        private fun updateCannon(event: MotionEvent) {
            val x: Float = event.x - game.getCannonCenter().x
            val y: Float = game.getCannonCenter().y - event.y
            val angle: Double = atan2(y.toDouble(), x.toDouble())
            game.setCannonAngle(angle.toFloat())
        }
    }
}