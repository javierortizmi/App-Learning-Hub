package com.javierortizmi.googlemapsusage

import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment : SupportMapFragment =
            supportFragmentManager.findFragmentById( R.id.map ) as SupportMapFragment
        fragment.getMapAsync( this )
    }

    override fun onMapReady(p0: GoogleMap) {
        Log.w( "MainActivity", "Inside onMapReady" )
        map = p0
        val whiteHouse = LatLng( 38.8977, -77.0366 )

        // add a circle
        val options = CircleOptions( )
        options.center( whiteHouse )
        options.radius( 100.0 )
        options.strokeWidth( 10.0f )
        options.strokeColor( Color.RED )
        map.addCircle( options )

        // add a marker
        // val marker : MarkerOptions = MarkerOptions( )
        // marker.position( whiteHouse )
        // marker.title( "White House" )
        // marker.snippet( "How is the food?" )
        // map.addMarker( marker )

        map.addMarker( MarkerOptions( ).position( whiteHouse ).title( "White House").snippet( "How is the food?" ) )

        val camera : CameraUpdate = CameraUpdateFactory.newLatLngZoom( whiteHouse, 16.0f )
        map.moveCamera( camera )

        // do some geocoding
        geocode()
    }

    private fun geocode( ) {
        val geocoder = Geocoder( this )
        val address = "xyz bad address"
        val handler = GeocodeHandler()
        geocoder.getFromLocationName( address, 5, handler )
    }

    inner class GeocodeHandler : Geocoder.GeocodeListener {
        override fun onGeocode(p0: MutableList<Address>) {
            if(p0.size > 0) {
                Log.w( "MainActivity", "we have results" )
                val firstResult : Address = p0[0]
                val lat : Double = firstResult.latitude
                val long : Double = firstResult.longitude
                Log.w( "MainActivity", "( lat, long ) is $lat, $long")
            }
        }

        override fun onError(errorMessage: String?) {
            super.onError(errorMessage)
            Log.w( "MainActivity", "error: $errorMessage")
        }
    }
}