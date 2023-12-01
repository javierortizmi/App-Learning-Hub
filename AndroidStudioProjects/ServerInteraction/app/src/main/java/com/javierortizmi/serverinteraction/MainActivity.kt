package com.javierortizmi.serverinteraction

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var tv: TextView               // Where the results will take place
    private lateinit var emailET: EditText
    private lateinit var nameET: EditText
    private lateinit var ageET: EditText
    private lateinit var writeTask: WriteServer   // Thread for write
    private lateinit var readTask: ReadServer     // Thread for read

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv = findViewById(R.id.tv)  // Locate the textView in the xml
        emailET = findViewById(R.id.email)
        nameET = findViewById(R.id.name)
        ageET = findViewById(R.id.age)
    }

    // Create and start the thread for WRITE
    fun write(v: View) {
        tv.setBackgroundColor(getColor(R.color.invisible))
        Log.w("Main Activity", "Inside WRITE $v")
        writeTask = WriteServer(this, emailET.text.toString(), nameET.text.toString(), ageET.text.toString())
        writeTask.start()
    }

    // Create and start the thread for READ
    fun read(v: View) {
        Log.w("Main Activity", "Inside READ $v")
        readTask = ReadServer(this, emailET.text.toString())
        readTask.start()
    }

    fun updateWriteViewWithJson(s: String) {
        Log.w("Main Activity", s)
        try {
            val jsonObject = JSONObject(s)
            Log.w("Main Activity", "$jsonObject")
            val result: String = jsonObject.getString("result")

            tv.text = result
        } catch (je: JSONException) {
            Log.w("MainActivity", "Json exception is " + je.message)
        }
    }

    fun updateReadViewWithJson(s: String) {
        Log.w("Main Activity", s)
        try {
            val jsonObject = JSONObject(s)
            Log.w("Main Activity", "$jsonObject")
            val found: String = jsonObject.getString("found")
            val data: JSONArray = jsonObject.getJSONArray("data")
            val name: String = data.getString(0)
            val number: Int = data.getInt(1)
            val color: String = data.getString(2)

            if (found == "yes") {
                tv.text = getString(R.string.readResult, name, number)
                if (color == "blue") tv.setBackgroundColor(getColor(R.color.blue))
                if (color == "cyan") tv.setBackgroundColor(getColor(R.color.cyan))
                if (color == "red") tv.setBackgroundColor(getColor(R.color.red))
                if (color == "green") tv.setBackgroundColor(getColor(R.color.green))
                if (color == "yellow") tv.setBackgroundColor(getColor(R.color.yellow))
            }
            else tv.text = getString(R.string.na)

        } catch (je: JSONException) {
            Log.w("MainActivity", "Json exception is " + je.message)
        }
    }

    companion object {
        const val URL_WRITE: String = "https://cmsc436-2301.cs.umd.edu/project5Write.php"
        const val URL_READ: String = "https://cmsc436-2301.cs.umd.edu/project5Read.php"
    }
}