package com.javierortizmi.xmlcontent

import android.util.Log
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

@Suppress("SameParameterValue")
class ParseTask(private var activity: MainActivity) : Thread() {
    private lateinit var handler: SAXHandler

    override fun run() {
        super.run()
        val url = MainActivity.URL
        val items : ArrayList<Item>? = accessData( url )
        activity.displayList( items )
        val updateGui = UpdateGui( )
        activity.runOnUiThread( updateGui )
    }

    private fun accessData(url : String ) : ArrayList<Item>? {
        return try {
            val factory : SAXParserFactory = SAXParserFactory.newInstance()
            val saxParser : SAXParser = factory.newSAXParser()
            handler = SAXHandler()
            // start parsing
            saxParser.parse( url, handler )
            handler.getItems()
        } catch( e : Exception ) {
            Log.w( "MainActivity", "error: " + e.message )
            null
        }
    }

    inner class UpdateGui : Runnable {
        override fun run() {
            activity.displayList( handler.getItems( ) )
        }
    }
}