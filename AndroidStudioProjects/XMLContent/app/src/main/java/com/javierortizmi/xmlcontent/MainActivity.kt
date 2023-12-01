package com.javierortizmi.xmlcontent

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class MainActivity : AppCompatActivity() {
    private lateinit var listView : ListView
    private lateinit var items : ArrayList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById( R.id.list_view )

        val factory : SAXParserFactory = SAXParserFactory.newInstance()
        val saxParser : SAXParser = factory.newSAXParser()

        val handler = SAXHandler()
        val iStream : InputStream = resources.openRawResource( R.raw.test )

        // start parsing
        saxParser.parse( iStream, handler )

        val items : ArrayList<Item> = handler.getItems()
        for( item in items ) {
            Log.w( "MainActivity", item.toString( ) )
        }

        val task = ParseTask( this )
        task.start()
    }

    fun displayList( items : ArrayList<Item>? ) {
        if( items != null ) {
            this.items = items
            // fill listView with titles of items
            val titles : ArrayList<String> = ArrayList()
            for( item in items ) {
                Log.w( "MainActivity", item.toString() )
                if(item.getTitle() != null)
                    titles.add( item.getTitle()!! )
            }
            val adapter : ArrayAdapter<String> =
                ArrayAdapter<String>( this, android.R.layout.simple_list_item_1 , titles )
            listView.adapter = adapter
        } else { // no data retrieved
            Toast.makeText( this, "No data retrieved", Toast.LENGTH_LONG ).show()
        }
    }

    /* inner class ListItemHandler : AdapterView.OnItemClickListener {
        override fun onItemClick(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {

        }

    } */

    companion object {
        const val URL : String = "https://www.feedforall.com/sample.xml"
    }
}