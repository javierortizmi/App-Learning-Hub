package com.javierortizmi.chronometeradvertising

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Chronometer
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

class MainActivity : AppCompatActivity() {

    private var started : Boolean = false
    private lateinit var startStopButton : AppCompatButton
    private lateinit var chrono : Chronometer

    private lateinit var adView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startStopButton = findViewById( R.id.start_stop )
        chrono = findViewById( R.id.stop_watch )

        createAd( )
    }

    fun startStop( v : View) {
        Log.w("MainActivity", "$v")
        if( !started ) {
            // start
            startStopButton.text = getString(R.string.stop)
            startStopButton.setBackgroundResource( R.drawable.stop_button)
            resetChrono()
            chrono.start()
        } else {
            startStopButton.text = getString(R.string.start)
            startStopButton.setBackgroundResource( R.drawable.start_button )
            chrono.stop()
        }
        started = !started
    }

    fun reset( v : View ) {
        Log.w("MainActivity", "$v")
        if( !started ) {
            chrono.base = SystemClock.elapsedRealtime()
        }
    }

    private fun resetChrono( ) {
        val chronoText : String = chrono.text.toString()
        val idleMilliseconds : Long = ClockUtility.milliseconds( chronoText )
        chrono.base = SystemClock.elapsedRealtime() - idleMilliseconds
    }

    private fun createAd( ) {
        adView = AdView( this )
        val adSize = AdSize( AdSize.FULL_WIDTH, AdSize.AUTO_HEIGHT )
        adView.setAdSize( adSize )

        val adUnitId = // "ca-app-pub-3940256099942544/1033173712"
            "ca-app-pub-3940256099942544/6300978111"
        adView.adUnitId = adUnitId

        val builder : AdRequest.Builder = AdRequest.Builder( )
        builder.addKeyword( "fitness" ).addKeyword( "workout" )
        val request : AdRequest = builder.build()

        // add adView to linear layout
        val layout : LinearLayout = findViewById( R.id.ad_view )
        layout.addView( adView )

        // load the ad
        adView.loadAd( request )
    }

    override fun onPause() {
        adView.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        adView.resume()
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }
}