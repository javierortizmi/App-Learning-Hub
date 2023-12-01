package com.javierortizmi.ballonsgame

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class MainActivity : AppCompatActivity() {
    private lateinit var gameView: GameView
    private lateinit var balloons: Balloons
    private lateinit var detector: GestureDetector
    private var statusBarHeight = 0

    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create SAX Parser and Handler
        val factory : SAXParserFactory = SAXParserFactory.newInstance()
        val saxParser : SAXParser = factory.newSAXParser()
        val handler = SAXHandler()
        val iStream : InputStream = resources.openRawResource( R.raw.balloons3)

        // start parsing
        saxParser.parse( iStream, handler )

        balloons = Balloons(handler.getBalloons())
        for (balloon in balloons.getBalloons()) {
            Log.w("MainActivity", balloon.toString())
        }

        // Retrieve status bar height
        val statusBarId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        statusBarHeight = resources.getDimensionPixelSize(statusBarId)

        // Create a GameView object
        gameView = GameView(this, balloons)

        // Set the view
        setContentView(gameView)

        // Set up event handling
        val touchHandler = TouchHandler()
        detector = GestureDetector(this, touchHandler)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if( event != null )
            detector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    inner class TouchHandler : GestureDetector.SimpleOnGestureListener ( ) {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            if (!balloons.isGameWon()) {
//                Log.w("MainActivity", "Number taps: ${balloons.getAttempts()}")
                balloons.updateBalloons(e, statusBarHeight)
                gameView.postInvalidate()
                if (balloons.isGameWon())
                    Toast.makeText(this@MainActivity, "YOU WON", Toast.LENGTH_LONG).show()
                else if (balloons.isGameLost())
                    finish()
            }
            return super.onSingleTapUp(e)
        }
    }
}