package com.javierortizmi.xmlcontent

import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class SAXHandler: DefaultHandler() {
    private var items : ArrayList<Item> = ArrayList()
    private var currentItem : Item? = null
    private var element : String = ""
    private var validText : Boolean = false

    override fun startElement( uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        super.startElement(uri, localName, qName, attributes)
        // Log.w( "MainActivity", "startElement = " + qName )
        element = qName!!
        validText = true
        if(qName == "item")
            currentItem = Item( "", "" )
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        super.endElement(uri, localName, qName)
        // Log.w( "MainActivity", "endElement = " + qName )
        validText = false
        if( qName != null && currentItem != null && qName == "item")
            items.add( currentItem!! )
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        super.characters(ch, start, length)
        if( ch != null ) {
            val text = String(ch, start, length )
            // Log.w( "MainActivity", "text is " + text )

            if( currentItem != null && validText && element == "title")
                currentItem!!.setTitle( text )
            else if( currentItem != null && validText && element == "link")
                currentItem!!.setLink( text )
        }
    }

    fun getItems( ) : ArrayList<Item> {
        return items
    }
}