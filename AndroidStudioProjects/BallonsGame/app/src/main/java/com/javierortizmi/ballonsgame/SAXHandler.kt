package com.javierortizmi.ballonsgame

import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class SAXHandler: DefaultHandler() {
    private var balloons: ArrayList<Balloon> = ArrayList()
    private lateinit var currentBalloon: Balloon
    private var element: String = ""
    private var validElement: Boolean = false

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        super.startElement(uri, localName, qName, attributes)

        // Log.w("MainActivity", "startElement = $qName")
        if (qName != null) {
            element = qName
            validElement = true
        } else {
            validElement = false
        }

        if (qName == "balloon")
            currentBalloon = Balloon(0 , 0, 0)
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        super.characters(ch, start, length)
        if (ch != null) {
            val text = String(ch, start, length)
            // Log.w("MainActivity", "text is $text")

            if (validElement && element == "x")
                currentBalloon.setX(text.toInt())
            else if (validElement && element == "y")
                currentBalloon.setY(text.toInt())
            else if (validElement && element == "radius")
                currentBalloon.setRadius(text.toInt())
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        super.endElement(uri, localName, qName)

        // Log.w("MainActivity", "endElement = $qName")
        validElement = false

        if (qName != null && qName == "balloon")
            balloons.add(currentBalloon)
    }

    fun getBalloons(): ArrayList<Balloon> {
        return balloons
    }
}