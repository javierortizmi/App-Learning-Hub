package com.javierortizmi.ponggame

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Point
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import java.util.Timer

class MainActivity : AppCompatActivity() {
    private lateinit var gameView: GameView
    private lateinit var pong: Pong

    private lateinit var pool: SoundPool
    private var hitSoundId: Int = 0
    private lateinit var detector: GestureDetector

    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve width and height of the screen
        val width = Resources.getSystem().displayMetrics.widthPixels
        val height = Resources.getSystem().displayMetrics.heightPixels

        // Retrieve status bar height
        val statusBarId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight: Int = resources.getDimensionPixelSize(statusBarId)

        // Create a GameView object with the dimensions of the screen
        gameView = GameView(this, width, height + statusBarHeight)
        // Get the Pong class from the gameView class
        pong = gameView.getPong()
        // Set the view
        setContentView(gameView)

        // Set up event handling
        val handler = TouchHandler()
        detector = GestureDetector(this, handler)

        // For sound
        val poolBuilder: SoundPool.Builder = SoundPool.Builder()
        pool = poolBuilder.build()
        hitSoundId = pool.load(this, R.raw.hit, 1)
    }

    fun setSchedule() {
        // Set schedule
        val gameTimer = Timer()
        val gameTimerTask = GameTimerTask(this)
        gameTimer.schedule(gameTimerTask, 0L, GameView.DELTA_TIME.toLong())
    }

    fun updateModel() {
        // Update model
        val gameMode = pong.getBall().checkCollision(gameView.getWidthValue(), gameView.getHeightValue(), pong.getPaddle())
        if (gameMode == 4) {
            gameView.setPreferences(this)
            gameView.setGameLost(true)
        }
        pong.getBall().moveBall(gameMode)
        if (pong.getPaddle().paddleCollisionCheck()) {
            pool.play(hitSoundId, 1.0f, 1.0f, 0, 0, 1.0f)
            gameView.newLevel()
            Log.w("Main Activity", "Level is: ${gameView.getLevel()}, Best Level is: ${gameView.getBestLevel()}")
        }
    }

    fun updateView() {
        // Update view
        gameView.postInvalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if( event != null )
            detector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    inner class TouchHandler : GestureDetector.SimpleOnGestureListener ( ) {

        private var initGame: Boolean = false
        override fun onDown(e: MotionEvent): Boolean {
            if (!initGame) {
                setSchedule()
                initGame = true
            }
            return super.onDown(e)
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            updatePaddle(e2)
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        private fun updatePaddle(event: MotionEvent) {
            pong.getPaddle().setPaddlePosition(Point(event.x.toInt(), pong.getPaddle().getPaddlePosition().y))
        }
    }
}