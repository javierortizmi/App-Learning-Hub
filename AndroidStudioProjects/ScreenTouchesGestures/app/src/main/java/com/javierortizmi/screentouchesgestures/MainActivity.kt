package com.javierortizmi.screentouchesgestures

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent

// Gestures
class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private lateinit var detector : GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        // set up event handling
        detector = GestureDetector( this, this )
        detector.setOnDoubleTapListener( this )
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // dispatch event to appropriate method
        detector.onTouchEvent( event!! )
        return super.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent): Boolean {
        Log.w( MA, "Inside onDown")
        return true
    }

    override fun onShowPress(e: MotionEvent) {
        Log.w( MA, "Inside onShowPress" )
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        Log.w( MA, "Inside onSingleTapUp" )
        return true
    }

    override fun onScroll( e1: MotionEvent?,
                           e2: MotionEvent,
                           distanceX: Float,
                           distanceY: Float
    ): Boolean {
        Log.w( MA, "Inside onScroll" )
        return true
    }

    override fun onLongPress(e: MotionEvent) {
        Log.w( MA, "Inside onLongPress" )
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.w( MA, "Inside onFling" )
        if( e1 != null ) {
            val deltaTime = e2.eventTime - e1.eventTime
            Log.w( MA, "raw Xs = " + e1.rawX + ", " + e2.rawX )
            Log.w(MA, "delta time (in ms ) + $deltaTime")
        }
        Log.w( MA, "vX (in pixels/second) = $velocityX" )
        Log.w( MA, "vY (in pixels/second) = $velocityY" )

        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        Log.w( MA, "Inside onSingleTapConfirmed" )
        return true
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        Log.w( MA, "Inside onDoubleTap, e = $e")
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        Log.w( MA, "Inside onDoubleTapEvent, e = $e")
        return true
    }

    companion object {
        const val MA : String = "MainActivity"
    }
}